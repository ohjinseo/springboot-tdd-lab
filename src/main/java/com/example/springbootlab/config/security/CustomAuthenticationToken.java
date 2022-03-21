package com.example.springbootlab.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String type;
    private CustomUserDetails principal;

    public CustomAuthenticationToken(String type, CustomUserDetails principal, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.type = type;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CustomUserDetails getPrincipal() {
        return principal;
    }

    public String getType(){
        return type;
    }
}
