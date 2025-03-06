package com.shikhon_mate.user.response;

import com.shikhon_mate.user.Role;
import com.shikhon_mate.user.model.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;

    public UserResponse( User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
