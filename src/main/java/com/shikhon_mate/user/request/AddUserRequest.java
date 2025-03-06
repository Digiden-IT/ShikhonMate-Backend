package com.shikhon_mate.user.request;

import com.shikhon_mate.user.Role;
import lombok.Data;

@Data
public class AddUserRequest {
    private String name;
    private String password;
    private String email;
    private Role role;
}
