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
        // Mocking input data
        long userId = 1L;
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setEmail("testemail@example.com");
        userUpdateRequest.setUsername("testusername");

        // Mocking the service response
        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(userId);
        updatedUserEntity.setEmail("testemail@example.com");
        updatedUserEntity.setUsername("testusername");
        when(userService.updateUser(anyLong(), any(UserUpdateRequest.class))).thenReturn(new UserResponse(updatedUserEntity));

        // Calling the controller method
        UserResponse responseEntity = userController.updateUser(userId, userUpdateRequest);

        // Verifying that the service method was called with the correct arguments
        verify(userService).updateUser(userId, userUpdateRequest);

        // Verifying the response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verifying the response body
        UserResponse responseBody = responseEntity.getBody();
        assertEquals(userId, responseBody.getId());
        assertEquals("testemail@example.com", responseBody.getEmail());
        assertEquals("testusername", responseBody.getUsername());

        // Logging
        System.out.println("Test passed successfully!");
        System.out.println("Updated Email: " + userUpdateRequest.getEmail());
        System.out.println("Updated Username: " + userUpdateRequest.getUsername());
    }
}
