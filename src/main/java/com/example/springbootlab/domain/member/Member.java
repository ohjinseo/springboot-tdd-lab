package com.example.springbootlab.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, unique = true, length=30)
    private String nickname;

    @Column(nullable = false, length=30, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    public Member(String email, String password, String username, String nickname){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
}
