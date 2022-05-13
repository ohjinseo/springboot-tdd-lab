package com.example.springbootlab.config.security.guard;

import com.example.springbootlab.domain.member.RoleType;
import com.example.springbootlab.domain.post.Post;
import com.example.springbootlab.domain.post.PostRepository;
import com.example.springbootlab.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostGuard {
    private final AuthHelper authHelper;
    private final PostRepository postRepository;

    public boolean check(Long id) {
        return authHelper.isAuthenticated() && hasAuthority(id);
    }

    // 관리자 권한 검사가, 자원의 소유자 검사보다 먼저 이뤄져야된다.
    // ==> DB 접근 비용을 최소화하기위해..
    private boolean hasAuthority(Long id) {
        return hasAdminRole() || isResourceOwner(id);
    }

    private boolean isResourceOwner(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new AccessDeniedException("");
        });
        Long memberId = authHelper.extractMemberId();
        return post.getMember().getId().equals(memberId);
    }

    private boolean hasAdminRole(){
        return authHelper.extractMemberRoles().contains(RoleType.ROLE_ADMIN);
    }
}
