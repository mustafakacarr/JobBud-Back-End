package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class RefreshRequest {
    private long userId;
    private String refreshToken;
}