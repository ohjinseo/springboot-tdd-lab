package com.example.springbootlab.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
}
