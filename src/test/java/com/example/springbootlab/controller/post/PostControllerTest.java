package com.example.springbootlab.controller.post;

import com.example.springbootlab.dto.post.PostCreateRequest;
import com.example.springbootlab.service.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.List;

import static com.example.springbootlab.factory.dto.PostCreateRequestFactory.createPostCreateRequestWithImages;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @InjectMocks
    PostController postController;
    @Mock
    PostService postService;
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void createTest() throws Exception {
        // given
        // @ModelAttribute로 전달받을 PostCreateRequest 캡쳐할 수 있도록 선언
        ArgumentCaptor<PostCreateRequest> postCreateRequestArgumentCaptor = ArgumentCaptor.forClass(PostCreateRequest.class);

        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
        );
        PostCreateRequest req = createPostCreateRequestWithImages(imageFiles);

        // when, then
        // multipart/form-data 요청 테스트
        mockMvc.perform(
                multipart("/api/posts")
                        .file("images", imageFiles.get(0).getBytes())
                        .file("images", imageFiles.get(1).getBytes())
                        .param("title", req.getTitle())
                        .param("content", req.getContent())
                        .param("price", String.valueOf(req.getPrice()))
                        .param("categoryId", String.valueOf(req.getCategoryId()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isCreated());

        verify(postService).create(postCreateRequestArgumentCaptor.capture()); // create 메소드가 호출되는지 검증하고 전달되는 인자 캡쳐

        PostCreateRequest capturedRequest = postCreateRequestArgumentCaptor.getValue();
        assertThat(capturedRequest.getImages().size()).isEqualTo(2);
    }

    @Test
    void readTest() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                get("/api/posts/{id}", id)
        ).andExpect(status().isOk());

        verify(postService).read(id);
    }

    @Test
    void deleteTest() throws Exception{
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                delete("/api/posts/{id}", id)
        ).andExpect(status().isOk());

        verify(postService).delete(id);
    }
}