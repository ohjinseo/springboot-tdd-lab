package com.example.springbootlab.domain.member;

import com.example.springbootlab.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    // OneToMany default : LAZY
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberRole> roles;

    @Builder
    public Member(String email, String password, String username, String nickname, List<Role> roles){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.roles = roles.stream().map(r -> new MemberRole(this, r)).collect(Collectors.toSet());
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
}
