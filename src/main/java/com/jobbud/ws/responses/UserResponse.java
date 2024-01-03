package com.jobbud.ws.responses;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.enums.UserType;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private UserType userType;

    public UserResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.userType = userEntity.getUserType();
    }
}
