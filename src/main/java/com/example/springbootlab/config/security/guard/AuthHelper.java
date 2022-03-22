package com.example.springbootlab.config.security.guard;

import com.example.springbootlab.config.security.CustomAuthenticationToken;
import com.example.springbootlab.config.security.CustomUserDetails;
import com.example.springbootlab.domain.member.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthHelper {

    public boolean isAuthenticated(){
        return getAuthentication() instanceof CustomAuthenticationToken &&
                getAuthentication().isAuthenticated();
    }

    public Long extractMemberId(){
        return Long.valueOf(getUserDetails().getUserId());
    }

    public Set<RoleType> extractMemberRoles(){
        return getUserDetails().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .map(strAuth -> RoleType.valueOf(strAuth))
                .collect(Collectors.toSet());
    }

    private CustomUserDetails getUserDetails(){
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
