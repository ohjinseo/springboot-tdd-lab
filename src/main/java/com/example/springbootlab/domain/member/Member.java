package com.example.springbootlab.domain.member;

import com.example.springbootlab.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
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

    // 연관관계 주인이 아닌 테이블에서 컬럼이 생성?
    @OneToMany(mappedBy = "member")
    private Set<MemberRole> roles;

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
