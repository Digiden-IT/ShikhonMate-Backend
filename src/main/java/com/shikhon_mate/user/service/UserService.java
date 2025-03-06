package com.shikhon_mate.user.service;

import com.shikhon_mate.user.model.User;
import com.shikhon_mate.user.repository.UserRepository;
import com.shikhon_mate.user.request.AddUserRequest;
import com.shikhon_mate.user.response.UserResponse;
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
