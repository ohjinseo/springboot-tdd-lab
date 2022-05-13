package com.example.springbootlab.controller.post;

import com.example.springbootlab.domain.category.Category;
import com.example.springbootlab.domain.category.CategoryRepository;
import com.example.springbootlab.domain.member.Member;
import com.example.springbootlab.domain.member.MemberRepository;
import com.example.springbootlab.domain.post.Post;
import com.example.springbootlab.domain.post.PostRepository;
import com.example.springbootlab.dto.post.PostCreateRequest;
import com.example.springbootlab.dto.sign.SignInResponse;
import com.example.springbootlab.exception.MemberNotFoundException;
import com.example.springbootlab.init.TestInitDB;
import com.example.springbootlab.service.sign.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.example.springbootlab.factory.dto.PostCreateRequestFactory.createPostCreateRequest;
import static com.example.springbootlab.factory.dto.SignInRequestFactory.createSignInRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@Transactional
public class PostControllerIntegrationTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestInitDB initDB;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    SignService signService;

    Member member;
    Category category;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        initDB.initDB();
        member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        category = categoryRepository.findAll().get(0);
    }

    @Test
    void createTest() throws Exception {
        // given
        SignInResponse signInRes = signService.signIn(createSignInRequest(member.getEmail(), initDB.getPassword()));
        PostCreateRequest req = createPostCreateRequest("title", "content", 1000L, member.getId(), category.getId(), List.of());

        // when, then
        mockMvc.perform(
                multipart("/api/posts")
                        .param("title", req.getTitle())
                        .param("content", req.getContent())
                        .param("price", String.valueOf(req.getPrice()))
                        .param("categoryId", String.valueOf(req.getCategoryId()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", signInRes.getAccessToken())
        ).andExpect(status().isCreated());

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getMember().getId()).isEqualTo(member.getId()); // 1
    }
}
