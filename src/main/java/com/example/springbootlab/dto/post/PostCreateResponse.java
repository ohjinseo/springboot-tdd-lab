package com.example.springbootlab.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateResponse {
    private Long id; // 단순히 생성된 게시글의 id 값 응답
}
