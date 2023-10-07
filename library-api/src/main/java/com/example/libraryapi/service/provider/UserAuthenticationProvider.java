package com.example.libraryapi.service.provider;

import com.example.libraryapi.dto.AuthClientDetails;
import com.example.libraryapi.exception.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.example.libraryapi.utill.ExceptionMessagesConstants.WRONG_CREDENTIALS_BY_EMAIL_ERROR_MESSAGE;


@RequiredArgsConstructor
@Setter
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException {

        val email = authentication.getName();
        val password = authentication.getCredentials().toString();

        var userDetails = (AuthClientDetails) userDetailsService.loadUserByUsername(email);
        if (password.equals(userDetails.getPassword())) {
            var token = new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
            token.setDetails(userDetails.getExternalId());
            return token;
        }
        throw new BadCredentialsException(WRONG_CREDENTIALS_BY_EMAIL_ERROR_MESSAGE.formatted(email));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
