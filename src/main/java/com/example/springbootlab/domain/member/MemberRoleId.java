package com.example.springbootlab.domain.member;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

// composite key -> 기본적으로 알파벳 순으로 key가 만들어짐
// 즉, member, role 순으로 key 생성
// 인덱스 구조가 첫번째 필드로 정렬된 뒤에, 두번째 필드로 정렬되기 때문에 
// 중복도가 높은 필드가 첫번째로 생성되면, 필터링되는 레코드가 적어서 인덱스 효과를 못봄
// 그래서 user, role로 이름을 지정할 시 중복도가 높은 role이 우선으로 정렬되기 때문에 member로 선언

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MemberRoleId implements Serializable {
    private Member member;
    private Role role;
}
