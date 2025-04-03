package org.chinoel.goormspring.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chinoel.goormspring.entity.Post;
import org.chinoel.goormspring.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrivatePostInterceptor implements HandlerInterceptor {

    private final PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        if (uri.matches("/post/\\d+")) {
            Long postId = extractPostId(uri);
            Post post = postService.getPost(postId);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (post.getAccessLevel().equals("PRIVATE") &&
                !post.getUser().getUsername().equals(authentication.getName())) {
                log.info("비밀글 접근 시도 : PostId={}, User={}", postId, authentication.getName());

                response.sendRedirect("/post?error=private");
                return false;
            }
        }
        return true;
    }

    private Long extractPostId(String uri) {
        try {
            String[] parts = uri.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            log.error("잘못된 URI 형식: {}", uri, e);
            return -1L; // 기본값 설정
        }
    }
}
