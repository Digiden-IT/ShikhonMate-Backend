package dev.jahid.user_auth_db_config_boilerplate.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshRequest {
    private String token;
}
