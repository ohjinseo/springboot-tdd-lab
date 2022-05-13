package com.example.springbootlab.dto.post;

import com.example.springbootlab.domain.post.Post;
import com.example.springbootlab.dto.member.MemberDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-DD'T'HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-DD'T'HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime modifiedAt;

    public static PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPrice(),
                MemberDto.toDto(post.getMember()),
                post.getImages().stream().map(i -> ImageDto.toDto(i)).collect(Collectors.toList()),
                post.getCreatedDate(),
                post.getModifiedDate()
        );
    }
}
