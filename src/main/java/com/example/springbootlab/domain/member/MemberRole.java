package com.example.springbootlab.domain.member;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 브릿지 엔티티

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(MemberRoleId.class) // composite key 생성
@EqualsAndHashCode // Member에서 Set으로 선언되었기 때문에 재정의
public class MemberRole {
    @Id
    @ManyToOne
    private Member member;

    @Id
    @ManyToOne
    private Role role;
}
