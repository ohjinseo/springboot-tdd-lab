package com.example.springbootlab.service.post;

import com.example.springbootlab.domain.category.CategoryRepository;
import com.example.springbootlab.domain.member.MemberRepository;
import com.example.springbootlab.domain.post.Image;
import com.example.springbootlab.domain.post.Post;
import com.example.springbootlab.domain.post.PostRepository;
import com.example.springbootlab.dto.post.PostCreateRequest;
import com.example.springbootlab.dto.post.PostCreateResponse;
import com.example.springbootlab.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Transactional
    public PostCreateResponse create(PostCreateRequest req) {
        Post post = postRepository.save(
                PostCreateRequest.toEntity(
                        req,
                        memberRepository,
                        categoryRepository
                )
        );

        uploadImage(post.getImages(), req.getImages());
        return new PostCreateResponse(post.getId());
    }

    private void uploadImage(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
    }
}
