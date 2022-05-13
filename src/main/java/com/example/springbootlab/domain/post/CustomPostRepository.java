package com.example.springbootlab.domain.post;

import com.example.springbootlab.dto.post.PostReadCondition;
import com.example.springbootlab.dto.post.PostSimpleDto;
import org.springframework.data.domain.Page;

public interface CustomPostRepository {
    Page<PostSimpleDto> findAllByCondition(PostReadCondition con);
}
