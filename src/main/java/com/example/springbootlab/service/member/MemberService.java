package com.example.springbootlab.service.member;

import com.example.springbootlab.domain.member.Member;
import com.example.springbootlab.domain.member.MemberRepository;
import com.example.springbootlab.dto.member.MemberDto;
import com.example.springbootlab.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly=true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto read(Long id){
        return MemberDto.toDto(memberRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }

    public void delete(Long id){
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }
}
