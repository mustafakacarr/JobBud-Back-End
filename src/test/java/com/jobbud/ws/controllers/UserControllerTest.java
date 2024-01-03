package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.requests.UserUpdateRequest;
import com.jobbud.ws.responses.UserResponse;
import com.jobbud.ws.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testUpdateUser() {
        long userId = 1L;
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setEmail("testemail@example.com");
        userUpdateRequest.setUsername("testusername");


        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(userId);
        updatedUserEntity.setEmail("testemail@example.com");
        updatedUserEntity.setUsername("testusername");
        when(userService.updateUser(anyLong(), any(UserUpdateRequest.class))).thenReturn(new UserResponse(updatedUserEntity));


        UserResponse responseEntity = userController.updateUser(userId, userUpdateRequest);

        verify(userService).updateUser(userId, userUpdateRequest);

        UserResponse responseBody = responseEntity;
        assertEquals(userId, responseBody.getId());
        assertEquals("testemail@example.com", responseBody.getEmail());
        assertEquals("testusername", responseBody.getUsername());

        System.out.println("Test passed successfully!");
        System.out.println("Updated Email: " + userUpdateRequest.getEmail());
        System.out.println("Updated Username: " + userUpdateRequest.getUsername());
    }
}
