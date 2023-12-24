package com.jobbud.ws.enums;

import org.springframework.security.core.GrantedAuthority;

;

public enum UserType implements GrantedAuthority {
    FREELANCER,
    CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}