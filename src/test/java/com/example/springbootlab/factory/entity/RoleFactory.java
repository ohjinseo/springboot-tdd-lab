package com.example.springbootlab.factory.entity;

import com.example.springbootlab.domain.member.Role;
import com.example.springbootlab.domain.member.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }
}
