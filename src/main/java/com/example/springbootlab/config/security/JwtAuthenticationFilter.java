package com.example.springbootlab.config.security;

import com.example.springbootlab.config.token.TokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenHelper accessTokenHelper;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = extractToken(request);
        if(validateAccessToken(token)){
            setAccessAuthentication(token);
        }
        chain.doFilter(request, response);
    }

    private String extractToken(ServletRequest request){
        return ((HttpServletRequest)request).getHeader("Authorization");
    }

    private boolean validateAccessToken(String token){
        return token != null && accessTokenHelper.validate(token);
    }

    // 토큰으로부터 찾은 사용자를 securityContext
    private void setAccessAuthentication(String token){
        String userId = accessTokenHelper.extractSubject(token);
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()));
    }
}
