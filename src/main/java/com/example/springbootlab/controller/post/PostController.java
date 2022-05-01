package com.example.springbootlab.controller.post;

import com.example.springbootlab.controller.response.Response;
import com.example.springbootlab.dto.post.PostCreateRequest;
import com.example.springbootlab.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Post Controller", tags = "Post")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시글 작성", notes = "게시글을 생성한다.")
    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @ModelAttribute PostCreateRequest req) { 
        // 이미지 같은 경우 Content-Type이 multipart/form-data를 이용하므로 ModelAttribute 사용
        // 만약 ModelAttribute에서 validation이 위배되면 BindException 예외 발생
        // BindException은 MethodArgumentNotValidException(@RequestBody 예외 클래스)의 상위 클래스
        return Response.success(postService.create(req));
    }
}
