package com.example.springbootlab.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void memberCreateAndRead(){
        Member member1 = Member.builder().username("jinseo").email("ojs@naver.com").nickname("jin").build();
        memberRepository.save(member1);

        Member findMember = memberRepository.findById(member1.getId()).orElseThrow(IllegalAccessError::new);
        findMember.updateNickname("editNickName");
        System.out.println(findMember.getNickname());
    }
}