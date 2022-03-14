package com.example.springbootlab.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor 
// bug fix : 테스트코드에서 objectMapper로 JSON으로 변환하는 과정에서 기본 생성자가 필요하므로 @NoArgsConstructor 추가 [InvalidDefinitionException]
public class SignInRequest {
    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    // 정책을 설정하면 정책이 바뀌었을 때 로그인을 못하는 상황 발생
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
