package com.example.springbootlab.factory.dto;

import com.example.springbootlab.dto.sign.SignInResponse;

public class SignInResponseFactory {
    public static SignInResponse createSignInResponse(String accessToken, String refreshToken) {
        return new SignInResponse(accessToken, refreshToken);
    }
}
