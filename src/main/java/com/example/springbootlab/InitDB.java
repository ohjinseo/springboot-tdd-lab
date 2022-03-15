package com.example.springbootlab;

import com.example.springbootlab.domain.member.*;
import com.example.springbootlab.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class InitDB {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // PostConstruct에서는 @Transactional과 같은 AOP가 적용되지 않는다.
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB(){
        log.info("initialize database");
        initRole();
        initTestAdmin();
        initTestMember();
    }

    private void initRole() {
        roleRepository.saveAll(
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }

    private void initTestAdmin() {
        // Bug : detached entity passed to persist
        // 각 리포지토리를 호출하는 메소드는 독립적인 트랜잭션에 의해 수행되어짐
        // 즉, Member에서 MemberRole에 대해 cascade 설정이 PERSIST이며,
        // RoleRepository.findByRoleType을 호출하여 조회된 Role을 Member의 roles에 적용하고 저장하려는데
        // Role은 이미 컨텍스트에서 분리되어있기 때문에, MemberRole을 저장하려고 해도 Role에 대해 제대로 된 인식을 못하고 오류 발생

        memberRepository.save(
                new Member("admin@admin.com", passwordEncoder.encode("123456a!"), "admin", "admin",
                        List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                                roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new)))
        );
    }

    private void initTestMember() {
        memberRepository.saveAll(
                List.of(
                        new Member("member1@member.com", passwordEncoder.encode("123456a!"), "member1", "member1",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))),
                        new Member("member2@member.com", passwordEncoder.encode("123456a!"), "member2", "member2",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new)))
                )
        );
    }
}
