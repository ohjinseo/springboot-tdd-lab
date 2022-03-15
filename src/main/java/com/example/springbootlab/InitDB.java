package com.example.springbootlab;

import com.example.springbootlab.domain.member.Role;
import com.example.springbootlab.domain.member.RoleRepository;
import com.example.springbootlab.domain.member.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class InitDB {
    private final RoleRepository roleRepository;

    @PostConstruct // 빈의 생성과 의존성 주입이 끝난 뒤에 수행할 초기화 코드를 지정
    public void initDB(){
        log.info("initialize database");
        initRole();
    }

    private void initRole() {
        roleRepository.saveAll(
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }
}
