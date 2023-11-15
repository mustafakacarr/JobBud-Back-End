package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserEntity createUser(UserEntity user) {
        return userService.createUser(user);
    }
    @PutMapping("/{userId}")
    public UserEntity updateUser(@PathVariable long userId, UserEntity user) {
        return userService.updateUser(userId, user);
    }
}
