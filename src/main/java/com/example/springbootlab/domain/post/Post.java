package com.example.springbootlab.domain.post;

import com.example.springbootlab.domain.BaseTimeEntity;
import com.example.springbootlab.domain.category.Category;
import com.example.springbootlab.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // 작성자가 삭제된다면 게시글 또한 제거
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // 카테고리가 삭제된다면 게시글 또한 제거
    private Category category;

    // 게시글이 처음 저장될 때, 게시글에 등록된 이미지들도 같이 저장되야 하므로 PERSIST로 설정
    // Image는 고아 객체가 되면, DB에서 삭제
    @OneToMany(mappedBy = "post", cascade=CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> images;

    public Post(String title, String content, Long price, Member member, Category category, List<Image> images) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.member = member;
        this.category = category;
        this.images = new ArrayList<>();
        addImages(images); // 4
    }

    private void addImages(List<Image> added) {
        added.stream().forEach(i -> {
            images.add(i);
            i.initPost(this);
        });
    }
}
