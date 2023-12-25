package com.jobbud.ws.requests;

import lombok.Data;

@Data
public class GetAccessTokenRequest {
    private String code;
}
