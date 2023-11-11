package com.jobbud.ws.services;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.requests.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
/*
This is a service class. We will use it for business logics.
Catch requests in controller and process them in service class.
When you need db operations, use repository.
 */
@Service
public class UserService {
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }


    /*
     * This method is used to login a user
     * !!! We will change it later with oauth (JWT authentication) !!!
     * Its temporary for now.
     */
    public ResponseEntity<String> login(AuthRequest authRequest) {
        UserEntity user = userRepository.findByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok("User signed in");
        }else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    }
