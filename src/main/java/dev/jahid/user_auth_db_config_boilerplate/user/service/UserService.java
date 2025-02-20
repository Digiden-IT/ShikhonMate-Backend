package dev.jahid.user_auth_db_config_boilerplate.user.service;

import dev.jahid.user_auth_db_config_boilerplate.user.model.User;
import dev.jahid.user_auth_db_config_boilerplate.user.repository.UserRepository;
import dev.jahid.user_auth_db_config_boilerplate.user.request.AddUserRequest;
import dev.jahid.user_auth_db_config_boilerplate.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse addUser(AddUserRequest addUserRequest ) {
        User user = new User( addUserRequest );
        user.setPassword( passwordEncoder.encode( addUserRequest.getPassword() ) );
        User createdUser = userRepository.save( user );
        return new UserResponse( createdUser );
    }
}
