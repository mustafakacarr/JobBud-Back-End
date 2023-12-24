package com.jobbud.ws.services;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.exceptions.NotFoundException;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.repositories.WalletRepository;
import com.jobbud.ws.requests.AuthRequest;
import com.jobbud.ws.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;


    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserResponse(user)).collect(Collectors.toList());
    }

    public UserResponse createUser(UserEntity user) {
        UserEntity createdUser = userRepository.save(user);

        if (createdUser != null) {

            WalletEntity wallet = new WalletEntity(createdUser);
            walletRepository.save(wallet);
        }
        return new UserResponse(createdUser);

    }
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public UserResponse updateUser(long userId, UserEntity user) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        return new UserResponse(userRepository.save(userEntity));

    }
}
