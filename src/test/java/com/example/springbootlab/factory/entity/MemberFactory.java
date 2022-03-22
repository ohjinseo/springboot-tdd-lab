package com.example.springbootlab.factory.entity;

import com.example.springbootlab.domain.member.Member;
import com.example.springbootlab.domain.member.Role;

import java.util.Collections;
import java.util.List;

public class MemberFactory {

    public static Member createMember() {
        return new Member("email@email.com", "123456a!", "username", "nmickname", Collections.emptyList());
    }

    public static Member createMember(String email, String password, String username, String nickname) {
        return new Member(email, password, username, nickname, Collections.emptyList());
    }

    public static Member createMemberWithRoles(List<Role> roles) {
        return new Member("email@email.com", "123456a!", "username", "nickname", roles);
    }
}
