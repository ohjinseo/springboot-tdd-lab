package com.example.springbootlab.domain.member;

import com.example.springbootlab.exception.RoleNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void createAndReadTest(){
        // given
        Role role = createRole();

        // when
        roleRepository.save(role);

        // then
        Role foundRole = roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new);
        assertThat(foundRole.getId()).isEqualTo(role.getId());
    }

    @Test
    void deleteTest(){
        // given
        Role role = roleRepository.save(createRole());

        // when
        roleRepository.delete(role);

        // then
        assertThatThrownBy(() -> roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new))
                .isInstanceOf(RoleNotFoundException.class);

    }

    @Test
    void uniqueRoleTypeTest(){
        // given
        roleRepository.save(createRole());

        // when, then
        assertThatThrownBy(() -> roleRepository.save(createRole())).isInstanceOf(DataIntegrityViolationException.class);
    }


    private Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }

    private void clear() {
        em.flush();
        em.clear();
    }
}