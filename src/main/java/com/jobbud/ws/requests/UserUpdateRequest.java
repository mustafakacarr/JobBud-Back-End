package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String email;
}
