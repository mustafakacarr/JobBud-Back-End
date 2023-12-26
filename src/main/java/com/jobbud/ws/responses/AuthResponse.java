package com.jobbud.ws.responses;


import com.jobbud.ws.enums.UserType;
import lombok.Data;

@Data
public class AuthResponse {
    String message;
    Long userId;
    String username;
    String email;
    UserType userType;
    String accessToken;
    String refreshToken;
}