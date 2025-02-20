package dev.jahid.user_auth_db_config_boilerplate.user.controller;

import dev.jahid.user_auth_db_config_boilerplate.user.request.AddUserRequest;
import dev.jahid.user_auth_db_config_boilerplate.user.response.UserResponse;
import dev.jahid.user_auth_db_config_boilerplate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor( onConstructor_ = @Autowired )
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody AddUserRequest request ) {
        UserResponse response = userService.addUser( request );
        return ResponseEntity.ok( response );
    }
}
