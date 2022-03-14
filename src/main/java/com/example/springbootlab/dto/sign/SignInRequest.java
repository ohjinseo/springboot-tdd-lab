package com.example.springbootlab.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
// bug fix : 테스트코드에서 objectMapper로 JSON으로 변환하는 과정에서 기본 생성자가 필요하므로 @NoArgsConstructor 추가 [InvalidDefinitionException]
public class SignInRequest {
    private String email;
    private String password;
}
