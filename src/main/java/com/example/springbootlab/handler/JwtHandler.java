package com.example.springbootlab.handler;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHandler {
    private String type = "Bearer";

    // Base64로 인코딩된 key 값, 토큰에 저장될 데이터 subject, 만료 기간 maxAgeSeconds
    public String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        Date now = new Date();
        return type + Jwts.builder() // jwt 빌드 시작
                .setSubject(subject)
                .setIssuedAt(now)   // 토큰 발급일을 지정
                .setExpiration(new Date(now.getTime() + maxAgeSeconds * 1000L)) // 만료 일자 지정 (ms 단위이므로 1000을 곱함)
                .signWith(SignatureAlgorithm.HS256, encodedKey) // 파라미터로 받은 key로 SHA-256알고리즘을 사용하여 서명
                .compact(); // 토큰 생성
    }

    public String extractSubject(String encodedKey, String token) {
        return parse(encodedKey, token).getBody().getSubject();
    }

    // 토큰 생성에 사용했던 key가 맞는지 확인
    public boolean validate(String encodedKey, String token) {
        try {
            parse(encodedKey, token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 정보의 한 덩어리를 클레임(Claim)이라고 부름
    private Jws<Claims> parse(String encodedKey, String token) {
        return Jwts.parser()
                .setSigningKey(encodedKey)
                .parseClaimsJws(untype(token)); // Bearer를 제외한 토큰 삽입
    }

    private String untype(String token){
        return token.substring(type.length());
    }
}
