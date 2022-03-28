package com.example.springbootlab.factory.entity;

import com.example.springbootlab.domain.category.Category;
import com.example.springbootlab.domain.member.Member;
import com.example.springbootlab.domain.post.Image;
import com.example.springbootlab.domain.post.Post;

import java.util.List;

import static com.example.springbootlab.factory.entity.CategoryFactory.createCategory;
import static com.example.springbootlab.factory.entity.MemberFactory.createMember;

public class PostFactory {
    public static Post createPost() {
        return createPost(createMember(), createCategory());
    }

    public static Post createPost(Member member, Category category) {
        return new Post("title", "content", 1000L, member, category, List.of());
    }

    public static Post createPostWithImages(Member member, Category category, List<Image> images) {
        return new Post("title", "content", 1000L, member, category, images);
    }

    public static Post createPostWithImages(List<Image> images) {
        return new Post("title", "content", 1000L, createMember(), createCategory(), images);
    }


}
