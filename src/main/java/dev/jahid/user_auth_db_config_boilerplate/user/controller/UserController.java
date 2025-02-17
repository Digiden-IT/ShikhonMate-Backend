package dev.jahid.user_auth_db_config_boilerplate.user.controller;

import dev.jahid.user_auth_db_config_boilerplate.user.request.AddUserRequest;
import dev.jahid.user_auth_db_config_boilerplate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
@RequiredArgsConstructor( onConstructor_ = @Autowired )
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser( @RequestBody AddUserRequest userRequest ) {
        userService.addUser( userRequest );
        return ResponseEntity.ok( "User added successfully" );
    }
}
