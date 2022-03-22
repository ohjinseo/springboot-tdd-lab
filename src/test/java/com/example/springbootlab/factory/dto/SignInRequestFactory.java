package com.example.springbootlab.factory.dto;

import com.example.springbootlab.dto.sign.SignInRequest;

public class SignInRequestFactory {
    public static SignInRequest createSignInRequest() {
        return new SignInRequest("email@email.com", "123456a!");
    }

    public static SignInRequest createSignInRequest(String email, String password) {
        return new SignInRequest(email, password);
    }

    public static SignInRequest createSignInRequestWithEmail(String email) {
        return new SignInRequest(email, "123456a!");
    }

    public static SignInRequest createSignInRequestWithPassword(String password) {
        return new SignInRequest("email@email.com", password);
    }
}
