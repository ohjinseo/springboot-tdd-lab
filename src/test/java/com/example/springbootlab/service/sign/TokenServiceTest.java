package com.example.springbootlab.service.sign;

import com.example.springbootlab.handler.JwtHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

// TokenService는 JwtHandler를 의존하고 있음
// 하지만 의존 관계에 있는 다른 객체들을 테스트 하려는 것이 아니고 TokenService만 테스트할 것이기 때문에
// JwtHandler는 테스트하는데 필요한 행위 또는 상태만 제공해주면 된다.
// 이를 위해 사용할 수 있는 것이 Mockito 프레임워크
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks // 의존성을 가지고 있는 객체들을 가짜로 만들어서 주입받을 수 있도록 함
    TokenService tokenService;

    @Mock // Mock 객체를 만들어서 @InjectMocks로 지정된 객체에 주입해줌
    JwtHandler jwtHandler;


    // ReflectionTestUtils -> setter 없이 private 필드에 값을 주입
    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(tokenService, "accessTokenMaxAgeSeconds", 10);
        ReflectionTestUtils.setField(tokenService, "refreshTokenMaxAgeSeconds", 10);
        ReflectionTestUtils.setField(tokenService, "accessKey", "accessKey");
        ReflectionTestUtils.setField(tokenService, "refreshKey", "refreshKey");
    }

    @Test // 엑세스 토큰 생성 테스트
    void createAccessTokenTest() {
        // given
        given(jwtHandler.createToken(anyString(), anyString(), anyLong())).willReturn("access");

        // when
        String token = tokenService.createAccessToken("subject");

        // then
        assertThat(token).isEqualTo("access");
        verify(jwtHandler).createToken(anyString(), anyString(), anyLong());
    }

    @Test // 리프레시 토큰 생성 테스트
    void createRefreshTokenTest(){
        // given
        given(jwtHandler.createToken(anyString(), anyString(), anyLong())).willReturn("refresh");

        // when
        String token = tokenService.createRefreshToken("subject");

        // then
        assertThat(token).isEqualTo("refresh");
        verify(jwtHandler).createToken(anyString(), anyString(), anyLong());
    }
}