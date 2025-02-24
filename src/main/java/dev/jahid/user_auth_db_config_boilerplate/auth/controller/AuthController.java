package dev.jahid.user_auth_db_config_boilerplate.auth.controller;

import dev.jahid.user_auth_db_config_boilerplate.auth.request.LoginRequest;
import dev.jahid.user_auth_db_config_boilerplate.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/auth" )
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class AuthController {

    private final AuthService authService;

    @PostMapping( "/login" )
    public ResponseEntity<?> login( @RequestBody LoginRequest loginRequest ) {
        return authService.login( loginRequest );
    }

}
