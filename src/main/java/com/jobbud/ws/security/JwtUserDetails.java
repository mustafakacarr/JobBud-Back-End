package com.jobbud.ws.security;

import com.jobbud.ws.entities.UserEntity;
import com.jobbud.ws.enums.UserType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Data
public class JwtUserDetails implements UserDetails {

    public long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;



    public JwtUserDetails(long id, String username, String password, List<GrantedAuthority> authoritiesList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authoritiesList;
    }
    public static JwtUserDetails create(UserEntity user){
        UserType userType = user.getUserType();
        List<GrantedAuthority> authoritiesList = Collections.singletonList(new SimpleGrantedAuthority(userType.name()));
        return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), authoritiesList);

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override

    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override

    public boolean isEnabled() {
        return true;
    }
}
