package dev.jahid.user_auth_db_config_boilerplate.security;

import dev.jahid.user_auth_db_config_boilerplate.security.filter.JwtAuthenticationFilter;
import dev.jahid.user_auth_db_config_boilerplate.security.service.CustomUserDetailsService;
import dev.jahid.user_auth_db_config_boilerplate.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[]  PUBLIC_API_ENDPOINTS = {
            "/auth/**",
            "/boilerplate-api-docs/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    public SecurityConfig( CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter ) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService( customUserDetailsService );
        provider.setPasswordEncoder( passwordEncoder() );
        return new ProviderManager( List.of( provider ) );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder( 16, 32, 1, 65536, 10 );
    }


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        return http
                .cors( Customizer.withDefaults() )
                .sessionManagement( AbstractHttpConfigurer::disable )
                .csrf( AbstractHttpConfigurer::disable )
                .httpBasic( AbstractHttpConfigurer::disable )
                .formLogin( AbstractHttpConfigurer::disable )
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers( PUBLIC_API_ENDPOINTS ).permitAll()
                                .requestMatchers( "/users" ).hasAuthority( Role.ADMIN.getName() )
                                .anyRequest().authenticated()
                )
                .addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
                .build();
    }
}
