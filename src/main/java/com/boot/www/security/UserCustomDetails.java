package com.boot.www.security;

import java.util.ArrayList;


import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.boot.www.auth.bean.UserVO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Component
public class UserCustomDetails implements UserDetails {

    private final UserVO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	log.debug("login : 333333333333333333333");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> ("ROLE_" + user.getAuth())); 
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPwd();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCustomDetails that = (UserCustomDetails) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}