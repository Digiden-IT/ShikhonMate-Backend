package dev.jahid.user_auth_db_config_boilerplate.user.request;

import dev.jahid.user_auth_db_config_boilerplate.user.Role;
import lombok.Data;

@Data
public class AddUserRequest {
    private String name;
    private String password;
    private String email;
    private Role role;
}
