package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;

//We created this class for simplify login request that came from front end.
// So if we didnt use this class we were using UserEntity class for request in controller.
// And like you know, UserEntity has some fields like these; id,username,email,password,type.
// When we use UserEntity for request, we have to send all of these fields from front end.
// So think that user has to send their usertype, id, email etc from login form...
}
