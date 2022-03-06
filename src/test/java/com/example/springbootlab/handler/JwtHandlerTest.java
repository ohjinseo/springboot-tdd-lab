package com.example.springbootlab.handler;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

class JwtHandlerTest {
    JwtHandler jwtHandler = new JwtHandler();

    @Test
    void createTokenTest() {
        // given 토큰을 생성할 때 사용할 키 생성
        String encodedKey = createEncodedKey();
        String subject = "subject"; // jwt에 담을 내용

        // when
        String token = createToken(encodedKey, subject, 0L);

        // then
        assertThat(token).contains("Bearer");
    }

    @Test
    void extractSubjectTest() {
        // given
        String encodedKey = createEncodedKey();
        String subject = "subject";
        String token = createToken(encodedKey, subject, 60L);

        // when
        String extractedSubject = jwtHandler.extractSubject(encodedKey, token);

        // then
        assertThat(extractedSubject).isEqualTo(subject);
    }

    @Test
    void invalidateByInvalidKeyTest() { // 토큰에 생성되었던 키 외에, 다른 키를 주었을 때 유효안한지?
        // given
        String encodedKey = createEncodedKey();
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate("invalid", token);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void invalidateByExpiredTokenTest() {
        // given
        String encodedKey = createEncodedKey();
        String token = createToken(encodedKey, "subject", 0L); // 만료 기간을 0으로 설정 후 유효한지 검사

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(isValid).isFalse();
    }

    private String createEncodedKey(){
        return Base64.getEncoder().encodeToString("myKey".getBytes());
    }

    private String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        return jwtHandler.createToken(encodedKey, subject, maxAgeSeconds);
    }

}