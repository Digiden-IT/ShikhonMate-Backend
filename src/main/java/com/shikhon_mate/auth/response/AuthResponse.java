package com.shikhon_mate.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    String accessToken;
    String refreshToken;
}
