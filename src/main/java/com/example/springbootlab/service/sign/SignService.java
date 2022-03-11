package com.example.springbootlab.service.sign;

import com.example.springbootlab.domain.member.*;
import com.example.springbootlab.dto.sign.SignInRequest;
import com.example.springbootlab.dto.sign.SignInResponse;
import com.example.springbootlab.dto.sign.SignUpRequest;
import com.example.springbootlab.exception.LoginFailureException;
import com.example.springbootlab.exception.MemberEmailAlreadyExistsException;
import com.example.springbootlab.exception.MemberNicknameAlreadyExistsException;
import com.example.springbootlab.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public void signUp(SignUpRequest req){
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new), passwordEncoder));
    }

    public SignInResponse signIn(SignInRequest req){
        // email로 Member를 찾지 못하든, 비밀번호가 올바르지 않든, 동일하게 LoginFailure 예외를 던짐
        // 그 이유는 어떤 항목에 의해 로그인에 실패하였는지 모르기 위함
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        String subject = createSubject(member);

        String accessToken = tokenService.createAccessToken(subject);
    }


    // 중복 검사와 비밀번호 검사는 비즈니스 로직에서 알아야할 사항임
    // 만약 Member 본인이 알게되면 MemberRepository, PasswordEncoder 등 알아야 할 내용이 너무 많아짐
    // 객체는 자신이 할 수 있는 일만 하면 되고, 자신이 알아야하는 내용만 알면됨
    // Member에 의존성을 추가하여 너무 많은 정보를 알게 하는 것은 좋은 방법이 아님
   private void validatePassword(SignInRequest req, Member member){
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())){
            throw new LoginFailureException();
        }
    }
    
    private String createSubject(Member member){
        return String.valueOf(member.getId());
    }

    private void validateSignUpInfo(SignUpRequest req){
        if(memberRepository.existsByEmail(req.getEmail())){
           throw new MemberEmailAlreadyExistsException(req.getEmail());
        }
        if(memberRepository.existsByNickname(req.getNickname())){
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
        }
    }
}
