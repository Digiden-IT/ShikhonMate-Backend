package dev.jahid.user_auth_db_config_boilerplate.security.service;

import dev.jahid.user_auth_db_config_boilerplate.security.CustomUserDetails;
import dev.jahid.user_auth_db_config_boilerplate.user.model.User;
import dev.jahid.user_auth_db_config_boilerplate.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException {
        User user = userRepository.findByEmail( email )
                .orElseThrow( () -> new UsernameNotFoundException( "User not found with email: " + email ) );

        return new CustomUserDetails( user );
    }
}
