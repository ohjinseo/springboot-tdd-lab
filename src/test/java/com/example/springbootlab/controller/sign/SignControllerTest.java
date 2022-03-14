package com.example.springbootlab.controller.sign;

import com.example.springbootlab.dto.sign.SignInRequest;
import com.example.springbootlab.dto.sign.SignInResponse;
import com.example.springbootlab.dto.sign.SignUpRequest;
import com.example.springbootlab.service.sign.SignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SignControllerTest {
    @InjectMocks
    SignController signController;

    @Mock
    SignService signService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(signController).build();
    }

    @Test // 회원가입 테스트
    void signUpTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("email", "password", "username", "nickname");

        // when, then
        mockMvc.perform(
                post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated());

        verify(signService).signUp(req);
    }

    @Test // 로그인 테스트
    void signInTest() throws Exception{
        // given
        SignInRequest req = new SignInRequest("email", "password");
        given(signService.signIn(req)).willReturn(new SignInResponse("access", "refresh"));

        // when, then
        mockMvc.perform(
                        post("/api/sign-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))

                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("access"))
                .andExpect(jsonPath("$.result.data.refreshToken").value("refresh"));

        verify(signService).signIn(req);
    }

    @Test // 회원가입 JSON 응답 시 null 필드 제거되는지 테스트
    void ignoreNullValueInJsonResponseTest() throws Exception{
        // given
        SignUpRequest req = new SignUpRequest("email", "password", "username", "nickname");

        // when
        mockMvc.perform(
                post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").doesNotExist());
    }
}