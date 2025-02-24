package dev.jahid.user_auth_db_config_boilerplate.auth.request;

import lombok.Data;

@Data
public class LoginRequest {
    public String email;
    public String password;
}
