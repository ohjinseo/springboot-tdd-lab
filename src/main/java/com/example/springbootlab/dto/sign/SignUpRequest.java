package com.example.springbootlab.dto.sign;

import com.example.springbootlab.domain.member.Member;
import com.example.springbootlab.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Data // 단순히 데이터 전달 용이므로 @Data 선언
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String email;

    private String password;

    private String username;

    private String nickname;

    public static Member toEntity(SignUpRequest req, Role role, PasswordEncoder encoder) {
        return Member.builder()
                .email(req.email)
                .password(encoder.encode(req.password))
                .username(req.username)
                .nickname(req.nickname)
                .roles(List.of(role)).build();
    }
}
