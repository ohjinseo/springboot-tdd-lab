package com.example.springbootlab.dto.post;

import com.example.springbootlab.domain.post.Post;
import com.example.springbootlab.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long price;
    private MemberDto member;
    private List<ImageDto> images;

    public static PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPrice(),
                MemberDto.toDto(post.getMember()),
                post.getImages().stream().map(i -> ImageDto.toDto(i)).collect(Collectors.toList())
        );
    }
}
