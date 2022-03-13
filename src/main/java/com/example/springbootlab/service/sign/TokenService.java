package com.example.springbootlab.service.sign;

import com.example.springbootlab.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// 설정 값과 jwtHandler를 이용해서 accessToken과 refreshToken을 발급해주는 서비스

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtHandler jwtHandler;

    // 설정 파일에 작성된 내용을 가져옴
    @Value("${jwt.max-age.access}")
    private long accessTokenMaxAgeSeconds;

    @Value("${jwt.max-age.refresh}")
    private long refreshTokenMaxAgeSeconds;

    @Value("${jwt.key.access}")
    private String accessKey;

    @Value("${jwt.key.refresh}")
    private String refreshKey;

    public String createAccessToken(String subject){
        return jwtHandler.createToken(accessKey, subject, accessTokenMaxAgeSeconds);
    }

    public String createRefreshToken(String subject) {
        return jwtHandler.createToken(refreshKey, subject, refreshTokenMaxAgeSeconds);
    }
}
