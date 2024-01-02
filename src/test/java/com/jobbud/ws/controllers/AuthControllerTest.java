package com.jobbud.ws.controllers;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.enums.UserType;
import com.jobbud.ws.requests.AuthRequest;
import com.jobbud.ws.requests.RegisterRequest;
import com.jobbud.ws.responses.AuthResponse;
import com.jobbud.ws.security.JwtTokenProvider;
import com.jobbud.ws.services.RefreshTokenService;
import com.jobbud.ws.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testLoginSuccess() throws AuthenticationException {
        // Arrange
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("password");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@example.com");
        userEntity.setUserType(UserType.CUSTOMER);

        when(authenticationManager.authenticate(any()))
                .thenReturn(Mockito.mock(org.springframework.security.core.Authentication.class));
        when(userService.getUserByUsername(authRequest.getUsername())).thenReturn(userEntity);
        when(jwtTokenProvider.generateJwtToken(any())).thenReturn("fakeJwtToken");
        when(refreshTokenService.createRefreshToken(any())).thenReturn("fakeRefreshToken");

        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.login(authRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AuthResponse authResponse = responseEntity.getBody();
        assertEquals("Bearer fakeJwtToken", authResponse.getAccessToken());
        assertEquals("fakeRefreshToken", authResponse.getRefreshToken());
        assertEquals("Authentication is succeed.", authResponse.getMessage());
        assertEquals(1L, authResponse.getUserId());
        assertEquals("testUser", authResponse.getUsername());
        assertEquals("test@example.com", authResponse.getEmail());
        assertEquals(UserType.CUSTOMER, authResponse.getUserType());
    }

    @Test
    public void testRegisterSuccess() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newUser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("newUser@example.com");
        registerRequest.setUserType(UserType.CUSTOMER);
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("newUser");
        authRequest.setPassword("password");
        UserEntity userEntity = new UserEntity();

        when(userService.getUserByUsername(any())).thenReturn(null);
        when(userService.getUserByUsername(registerRequest.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");


        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setId(2L);
        savedUserEntity.setUsername("newUser");
        savedUserEntity.setEmail("newUser@example.com");
        savedUserEntity.setUserType(UserType.CUSTOMER);

        when(authenticationManager.authenticate(any()))
                .thenReturn(Mockito.mock(org.springframework.security.core.Authentication.class));
        when(userService.getUserByUsername(authRequest.getUsername())).thenReturn(userEntity);
        when(jwtTokenProvider.generateJwtToken(any())).thenReturn("fakeJwtToken");
        when(refreshTokenService.createRefreshToken(any())).thenReturn("fakeRefreshToken");

        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        AuthResponse authResponse = responseEntity.getBody();
        assertEquals("User successfully registered.", authResponse.getMessage());
        assertEquals("Bearer fakeJwtToken", authResponse.getAccessToken());
        assertEquals("fakeRefreshToken", authResponse.getRefreshToken());
        assertEquals(2L, authResponse.getUserId());
        assertEquals("newUser", authResponse.getUsername());
        assertEquals("newUser@example.com", authResponse.getEmail());
        assertEquals(UserType.CUSTOMER, authResponse.getUserType());
    }

    @Test
    public void testRegisterUsernameAlreadyInUse() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existingUser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("existingUser@example.com");
        registerRequest.setUserType(UserType.CUSTOMER);

        when(userService.getUserByUsername(registerRequest.getUsername())).thenReturn(new UserEntity());

        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        AuthResponse authResponse = responseEntity.getBody();
        assert authResponse != null;
        assertEquals("Username already in use.", authResponse.getMessage());
    }}

