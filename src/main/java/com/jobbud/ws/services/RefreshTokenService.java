package com.jobbud.ws.services;


import com.google.api.client.util.Value;
import com.jobbud.ws.entities.RefreshToken;
import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;


    @Value("${refresh.token.expires.in}")
    long expireSeconds;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public boolean isRefreshExpired(RefreshToken refreshToken) {
        return refreshToken.getExpireDate().before(new Date());
    }

    public String createRefreshToken(UserEntity user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        RefreshToken refreshTokenOfUser = refreshTokenRepository.findByUserId(user.getId());
        if (refreshTokenOfUser != null)
            refreshToken.setId(refreshTokenOfUser.getId());
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken getByUserId(long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}