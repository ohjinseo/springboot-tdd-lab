package com.example.springbootlab.domain.member;

import com.example.springbootlab.exception.MemberNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        // when
        memberRepository.save(member);

        // then
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(foundMember.getId()).isEqualTo(member.getId());
    }

    @Test
    @Transactional
    public void memberUpdateTest() {
        // given
        String updateNickName = "update";
        Member member = memberRepository.save(createMember());
        clear();

        // when
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        foundMember.updateNickname(updateNickName);
        clear();

        // then
        Member updatedMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(updatedMember.getNickname()).isEqualTo(updateNickName);
    }

    @Test
    public void memberDeleteTest() {
        // given
        Member member = memberRepository.save(createMember());

        // when
        memberRepository.deleteById(member.getId());

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).isEmpty();
    }

    @Test
    public void uniqueEmailTest(){
        // given
        Member member = memberRepository.save(createMember());
        Member member2 = createMember(member.getEmail(),
                "nickname2",
                "username2",
                "password2");

        // then
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
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

    private void clear() {
        em.flush(); // 쿼리 수행
        em.clear(); // 캐시 비움
    }
}