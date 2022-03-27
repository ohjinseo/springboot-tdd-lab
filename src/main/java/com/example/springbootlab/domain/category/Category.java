package com.example.springbootlab.domain.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/*
* @OneToMany는 JPA로 인해 만들어지는 양방향 의존 관계이다.
* 하지만 실제 연관 관계의 주인은 Member(Team) 쪽이기 때문에 Team(One) 쪽에서 반드시 Member를 의존하고 있을 이유가 없다.
* 단지 cascade remove로 연쇄적 삭제하기 위해 새로운 의존 관계를 만들어줘야 된다는 것이다.
*
* 양방향 의존 관계를 무분별하게 설정하면, @ToString이나 @EqualsAndHashCode 등 서로를 계속 참조하며, 스택오버플로우가 발생하는 문제가 발생한다.
* 이러한 점에서 @OnDelete 방식이 우위를 점한다. (굳이 OneToMany 설정이 불필요)
*
* 또한 onDelete 방식은 JPA에서 단일 DELETE 쿼리만 전송하여 참조 레코드들을 연쇄적으로 제거해주는 반면,
* cascade remove방식은 JPA에서 FK를 통해 참조하는 레코드들을 제거하기 위해 그 개수만큼 DELETE 쿼리를 전송해야한다.
* */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(length=30, nullable = false)
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category parent; // NULL이라면 루트 카테고리를 의미

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
