package com.jobbud.ws.services;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.repositories.UserRepository;
import com.jobbud.ws.repositories.WalletRepository;
import com.jobbud.ws.requests.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;


    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(UserEntity user) {

        UserEntity createdUser = userRepository.save(user);
        if (createdUser != null) {
            WalletEntity wallet = new WalletEntity(createdUser);
            walletRepository.save(wallet);
        }
        return createdUser;
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
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

}
