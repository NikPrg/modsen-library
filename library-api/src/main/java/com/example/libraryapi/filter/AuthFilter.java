package com.example.libraryapi.filter;

import com.example.libraryapi.exception.AuthenticationException;
import com.example.libraryapi.exception.MissingAuthHeaderException;
import com.example.libraryapi.model.TokenType;
import com.example.libraryapi.service.security.AuthenticationService;
import com.example.libraryapi.service.security.impl.AuthenticationServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.libraryapi.utill.ExceptionMessagesConstants.AUTHORIZATION_HEADER_NOT_PROVIDED;
import static com.example.libraryapi.utill.ExceptionMessagesConstants.USER_NOT_AUTHENTICATED_ERROR_MESSAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private Map<PathPattern, List<String>> openedRoutes;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("""
                            Http request is passing auth filter:
                            Path - '{}'.
                            Method - '{}'.
                        """,
                request.getServletPath(),
                request.getMethod());

        if (openedRoutes.entrySet().stream().noneMatch(route -> isOpenedRoute(request, route))) {
            String bearerToken = request.getHeader(AUTHORIZATION);

            if (StringUtils.hasText(bearerToken)) {
                String token = bearerToken.replaceFirst(TokenType.BEARER.getValue(), "").trim();

                var id = authenticationService.isAuthenticated(token);

                if (!StringUtils.hasText(id)) {
                    throw new AuthenticationException(USER_NOT_AUTHENTICATED_ERROR_MESSAGE);
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        AuthenticationServiceImpl.AUTHORIZED_CLIENTS_DATA.get(id),
                        null,
                        AuthenticationServiceImpl.AUTHORIZED_CLIENTS_DATA.get(id).authorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                throw new MissingAuthHeaderException(AUTHORIZATION_HEADER_NOT_PROVIDED);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isOpenedRoute(HttpServletRequest request,
                                  Map.Entry<PathPattern, List<String>> route) {
        return route.getKey().matches(PathContainer.parsePath(request.getServletPath()))
                && route.getValue().contains(request.getMethod());
    }
}
