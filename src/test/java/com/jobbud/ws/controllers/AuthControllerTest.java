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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testLoginSuccess() throws AuthenticationException {

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("password");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@example.com");
        userEntity.setUserType(UserType.CUSTOMER);

        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(org.springframework.security.core.Authentication.class));
        when(userService.getUserByUsername(authRequest.getUsername())).thenReturn(userEntity);
        when(jwtTokenProvider.generateJwtToken(any())).thenReturn("fakeJwtToken");
        when(refreshTokenService.createRefreshToken(any())).thenReturn("fakeRefreshToken");


        ResponseEntity<AuthResponse> responseEntity = authController.login(authRequest);

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
    public void testRegister() {
        // Mocking necessary dependencies
        UserService userService = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
        RefreshTokenService refreshTokenService = mock(RefreshTokenService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        AuthController authController = new AuthController(authenticationManager, jwtTokenProvider, userService, passwordEncoder, refreshTokenService);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testUser");
        registerRequest.setPassword("testPassword");
        registerRequest.setEmail("test@mail.com");
        registerRequest.setUserType(UserType.CUSTOMER);

        when(userService.getUserByUsername(registerRequest.getUsername())).thenReturn(null);

        when(refreshTokenService.createRefreshToken(any(UserEntity.class))).thenReturn("mockedRefreshToken");

        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("mockedEncodedPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));

        when(jwtTokenProvider.generateJwtToken(any(Authentication.class))).thenReturn("mockedJwtToken");

        ResponseEntity<AuthResponse> responseEntity = authController.register(registerRequest);

        System.out.println("HTTP Status Code: " + responseEntity.getStatusCode());
        System.out.println("Response Body: " + responseEntity.getBody());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("User successfully registered.", responseEntity.getBody().getMessage());


        verify(userService, times(1)).createUser(any(UserEntity.class));
        verify(refreshTokenService, times(1)).createRefreshToken(any(UserEntity.class));
        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).generateJwtToken(any(Authentication.class));
    }}

