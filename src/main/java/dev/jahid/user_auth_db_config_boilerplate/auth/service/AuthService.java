package dev.jahid.user_auth_db_config_boilerplate.auth.service;

import dev.jahid.user_auth_db_config_boilerplate.auth.request.LoginRequest;
import dev.jahid.user_auth_db_config_boilerplate.auth.response.AuthResponse;
import dev.jahid.user_auth_db_config_boilerplate.security.CustomUserDetails;
import dev.jahid.user_auth_db_config_boilerplate.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public ResponseEntity<?> login( LoginRequest loginRequest ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( loginRequest.getEmail(), loginRequest.getPassword() )
        );
        SecurityContextHolder.getContext().setAuthentication( authentication );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken( userDetails );
        String refreshToken = jwtUtil.generateRefreshToken( userDetails );

        return ResponseEntity.ok( new AuthResponse(accessToken, refreshToken ) );
    }
}
