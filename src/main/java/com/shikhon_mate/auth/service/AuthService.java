package com.shikhon_mate.auth.service;

import com.shikhon_mate.auth.request.LoginRequest;
import com.shikhon_mate.auth.request.RefreshRequest;
import com.shikhon_mate.auth.response.AuthResponse;
import com.shikhon_mate.security.CustomUserDetails;
import com.shikhon_mate.security.JwtUtil;
import com.shikhon_mate.user.model.User;
import com.shikhon_mate.user.repository.UserRepository;
import com.shikhon_mate.user.service.LoggedInUserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LoggedInUserService loggedInUserService;
    private final UserRepository userRepository;


    @Transactional
    public ResponseEntity<?> login( LoginRequest loginRequest ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( loginRequest.getEmail(), loginRequest.getPassword() )
        );
        SecurityContextHolder.getContext().setAuthentication( authentication );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken( userDetails );
        String refreshToken = jwtUtil.generateRefreshToken( userDetails );
        String encryptedRefreshToken = passwordEncoder.encode( refreshToken );

        userRepository.updateRefreshTokenById( userDetails.getUser().getId(), encryptedRefreshToken );

        return ResponseEntity.ok( new AuthResponse( accessToken, refreshToken ) );
    }

    public AuthResponse refresh( RefreshRequest refreshRequest ) throws AuthenticationException {
        CustomUserDetails userDetails = jwtUtil.extractUser( refreshRequest.getToken() );

        if( !passwordEncoder.matches( refreshRequest.getToken(), userDetails.getUser().getRefreshToken() ) ) {
            throw new AuthenticationException( "Invalid token" );
        }

        String accessToken = jwtUtil.generateAccessToken( userDetails );
        return new AuthResponse( accessToken, refreshRequest.getToken() );
    }

    @Transactional
    public void logout() {
        User user = loggedInUserService.getLoginUser();
        userRepository.updateRefreshTokenById( user.getId(), null );
    }
}
