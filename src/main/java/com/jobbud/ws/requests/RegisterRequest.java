package com.jobbud.ws.requests;


import com.jobbud.ws.enums.UserType;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private UserType userType;
}
