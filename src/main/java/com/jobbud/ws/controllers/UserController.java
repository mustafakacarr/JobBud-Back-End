package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.requests.UserUpdateRequest;
import com.jobbud.ws.responses.UserResponse;
import com.jobbud.ws.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users")
@SecurityRequirement(name = "JobBud Auth with Jwt")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable long userId,@RequestBody UserUpdateRequest user) {
        return userService.updateUser(userId, user);
    }
}
