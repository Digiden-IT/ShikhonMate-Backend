package dev.jahid.user_auth_db_config_boilerplate.user.service;

import dev.jahid.user_auth_db_config_boilerplate.user.model.User;
import dev.jahid.user_auth_db_config_boilerplate.user.repository.UserRepository;
import dev.jahid.user_auth_db_config_boilerplate.user.request.AddUserRequest;
import dev.jahid.user_auth_db_config_boilerplate.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse addUser(AddUserRequest addUserRequest ) {
        User user = new User( addUserRequest );
        user.setPassword( passwordEncoder.encode( addUserRequest.getPassword() ) );
        user = userRepository.save( user );
        return new UserResponse( user );
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map( UserResponse::new )
                .collect( Collectors.toList() );
    }

    public UserResponse getUser( Long id ) {
        return new UserResponse( Objects.requireNonNull( userRepository.findById( id ).orElse( null ) ) );
    }
}
