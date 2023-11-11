package com.jobbud.ws.controllers;

import com.jobbud.ws.requests.AuthRequest;
import com.jobbud.ws.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/auth")
//Base endpoint url for this controller.
public class AuthController {
    private UserService userService;

    //Its automatically injects UserService.
    //Please dont use "field injection". Its not recommended even by Spring Boot.
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /*
     * This method used to login a user
     * !!! We will change it later with oauth (JWT authentication)
     * Its temporary for now. Cuz of its not best practice and definitely not safety!!!
     *
     */
    @PostMapping("/login")
    //Post request to /api/v1.0/auth/login
    public ResponseEntity<String> login(AuthRequest authRequest) {
        return userService.login(authRequest);
    }


}
