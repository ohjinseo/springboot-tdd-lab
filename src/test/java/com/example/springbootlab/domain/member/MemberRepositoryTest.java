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
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void memberCreateAndReadTest(){
        // given
        Member member = createMember(); // 지역변수인데도

        // when
        memberRepository.save(member); // DB에 저장
        // 영속성 컨텍스트에는 멤버 존재 X
        // DB 메모리에 저장됨 - 영속성 컨텍스트와는 다른 공간
        System.out.println(member.getId());

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

    @Test
    @Transactional
    void memberRoleCascadePersistTest() {
        // given
        List<RoleType> roleTypes = List.of(RoleType.ROLE_NORMAL, RoleType.ROLE_SPECIAL_BUYER, RoleType.ROLE_ADMIN);
        List<Role> roles = roleTypes.stream().map(roleType -> new Role(roleType)).collect(Collectors.toList());
        roleRepository.saveAll(roles);


        // 영속성 컨텍스트 내에서 관리되지 않을 시 roleRepository.findAll() 값을 Member의 role에 적용하고 저장하려는데,
        // 이미 roleRepository.findall() 값은 attached 상태이므로 cascade하게 PERSIST가 되지 않아 detached entity passed to persist 오류가 발생한다
        Member member = memberRepository.save(createMemberWithRoles(roleRepository.findAll())); 

        // when
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        Set<MemberRole> memberRoles = foundMember.getRoles();
        // 영속성 컨텍스트에서 관리되고 있지 않을 시 준영속상태가 되므로 프록시 객체는 진짜 값들을 가져올 수 없는 상태에 놓임
        // EAGER 전략일 때 왜 memberRoles를 가져오지 못하는걸까?? -> memberRoles가 attached 상태임

        System.out.println(memberRoles);
        System.out.println("------------");

        // then
        assertThat(memberRoles.size()).isEqualTo(roleTypes.size());
    }

    // 영속성 전이 테스트 : Member를 제거할 때 MemberRole 또한 함께 제거되는지??
    @Test
    @Transactional
    void memberRoleCascadeDeleteTest(){
        // given
        List<RoleType> roleTypes = List.of(RoleType.ROLE_NORMAL, RoleType.ROLE_SPECIAL_BUYER, RoleType.ROLE_ADMIN);
        List<Role> roles = roleTypes.stream().map(roleType -> new Role(roleType)).collect(Collectors.toList());
        roleRepository.saveAll(roles);

        Member member = memberRepository.save(createMemberWithRoles(roleRepository.findAll()));
        clear();

        // when
        memberRepository.deleteById(member.getId());
        clear();

        // then
        List<MemberRole> result = em.createQuery("select mr from MemberRole mr", MemberRole.class).getResultList();
        assertThat(result.size()).isZero();
    }

    private Member createMemberWithRoles(List<Role> roles) {
        return Member.builder()
                .email("email")
                .nickname("nickname")
                .username("username")
                .password("password")
                .roles(roles)
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