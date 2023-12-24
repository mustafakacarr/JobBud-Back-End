package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class RefreshRequest {
    long userId;
    String refreshToken;
}