package com.example.springbootlab.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void memberCreateAndReadTest(){
        // given
        Member member = createMember();
        System.out.println("------------");
        System.out.println(member.getId());

        // when
        memberRepository.save(member);

        System.out.println(member.getId());
        System.out.println(member.getCreatedDate());
        System.out.println("-----------");

        // then
    }

    private Member createMember(String email, String nickname, String username, String password) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .username(username)
                .password(password)
                .build();
    }

    private Member createMember(){
        return Member.builder()
                .email("email")
                .nickname("nickname")
                .username("username")
                .password("password")
                .build();
    }
}