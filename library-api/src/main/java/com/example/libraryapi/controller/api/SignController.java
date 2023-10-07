package com.example.libraryapi.controller.api;

import com.example.libraryapi.dto.AuthClientData;
import com.example.libraryapi.dto.UserLoginRequest;
import com.example.libraryapi.service.security.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapi.utill.HttpUtils.PUBLIC_API_V1;

@RestController
@RequestMapping(PUBLIC_API_V1)
@RequiredArgsConstructor
public class SignController {
    private final AuthenticationService authenticationService;

    @PostMapping("/users/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthClientData login(@RequestBody @Valid UserLoginRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/users/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request) {
        authenticationService.deauthenticate(request);
    }

}
