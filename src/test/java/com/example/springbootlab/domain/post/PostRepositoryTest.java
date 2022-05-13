package com.example.springbootlab.domain.post;


import com.example.springbootlab.domain.category.Category;
import com.example.springbootlab.domain.category.CategoryRepository;
import com.example.springbootlab.domain.member.Member;
import com.example.springbootlab.domain.member.MemberRepository;
import com.example.springbootlab.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.example.springbootlab.factory.entity.CategoryFactory.createCategory;
import static com.example.springbootlab.factory.entity.ImageFactory.createImage;
import static com.example.springbootlab.factory.entity.MemberFactory.createMember;
import static com.example.springbootlab.factory.entity.PostFactory.createPost;
import static com.example.springbootlab.factory.entity.PostFactory.createPostWithImages;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageRepository imageRepository;
    @PersistenceContext
    EntityManager em;

    Member member;
    Category category;

    @BeforeEach
    void beforeEach() {
        member = memberRepository.save(createMember());
        category = categoryRepository.save(createCategory());
    }

    @Test
    void findByIdWithMemberTest() {
        // given
        Post post = postRepository.save(createPost(member, category));

        // when
        Post foundPost = postRepository.findByIdWithMember(post.getId()).orElseThrow(PostNotFoundException::new);

        // then
        Member foundMember = foundPost.getMember();
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void createAndReadTest() {
        // given
        Post post = postRepository.save(createPost(member, category));

        // when
        Post foundPost = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);

        // then
        assertThat(foundPost.getId()).isEqualTo(post.getId());
        assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void deleteTest() {
        // given
        Post post = postRepository.save(createPost(member, category));

        // when
        postRepository.deleteById(post.getId());

        // then
        assertThatThrownBy(() -> postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test // 이미지가 연쇄적으로 생성되는지
    @Transactional
    void createCascadeImageTest() {
        // given
        Post post = postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));

        // when
        Post foundPost = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);

        // then
        List<Image> images = imageRepository.findAll();
        assertThat(images.size()).isEqualTo(2);
    }

    @Test
    @Transactional // 이미지가 연쇄적으로 삭제되는지
    void deleteCascadeImageTest() {
        // given
        Post post = postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));

        // when
        postRepository.deleteById(post.getId());

        // then
        List<Image> images = imageRepository.findAll();
        assertThat(images.size()).isZero();
    }

    @Test // Member가 삭제되었을 때 연쇄적으로 Post가 삭제되는지
    @Transactional
    void deleteCascadeByMemberTest() {
        // given
        postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));
        clear();

        // when
        memberRepository.deleteById(member.getId());
        clear();

        // then
        List<Post> result = postRepository.findAll();
        assertThat(result.size()).isZero();
    }

    @Test
    @Transactional
    void deleteCascadeByCategoryTest() {
        // given
        postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage()))); // post 저장
        clear();

        // when
        categoryRepository.deleteById(category.getId());
        clear();

        // then
        List<Post> result = postRepository.findAll();
        assertThat(result.size()).isZero();
    }

    void clear() {
        em.flush();
        em.clear();
    }
}