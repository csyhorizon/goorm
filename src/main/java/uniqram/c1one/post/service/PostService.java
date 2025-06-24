package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.dto.HomePostResponse;
import uniqram.c1one.post.dto.PostRequest;
import uniqram.c1one.post.dto.PostResponse;
import uniqram.c1one.post.dto.UserPostResponse;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostMedia;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.PostMediaRepository;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMediaRepository postMediaRepository;

    @Transactional
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(PostErrorCode.USER_NOT_FOUND));

        // 이미지 필수
        if (postRequest.getMediaUrls() == null || postRequest.getMediaUrls().isEmpty()) {
            throw new PostException(PostErrorCode.IMAGE_REQUIRED);
        }

        Post post = Post.of(user, postRequest.getContent(), postRequest.getLocation());
        postRepository.save(post);

        // 이미지 등록
        if (postRequest.getMediaUrls() != null && !postRequest.getMediaUrls().isEmpty()) {
            List<PostMedia> mediaList = postRequest.getMediaUrls().stream()
                    .map(url -> PostMedia.of(post, url))
                    .toList();
            postMediaRepository.saveAll(mediaList);
        }

        List<String> mediaUrls = postMediaRepository.findByPostIdOrderByIdAsc(post.getId())
                .stream()
                .map(PostMedia::getMediaUrl)
                .collect(Collectors.toList());

        return PostResponse.from(post, mediaUrls);
    }

    @Transactional(readOnly = true) // TODO: N+1 문제 발생 가능성 있음. PostMedia IN 쿼리로 개선 예정.
    public Page<HomePostResponse> getHomePosts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postRepository.findAllByOrderByIdDesc(pageable);

        return postPage.map(post -> {
            List<String> mediaUrls = postMediaRepository.findByPostIdOrderByIdAsc(post.getId())
                    .stream()
                    .map(PostMedia::getMediaUrl)
                    .collect(Collectors.toList());

            return HomePostResponse.from(post, mediaUrls);
        });
    }

    @Transactional(readOnly = true) // TODO: N+1 문제 발생 가능성 있음. PostMedia IN 쿼리로 개선 예정.
    public Page<UserPostResponse> getUserPosts(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postRepository.findByUserIdOrderByIdDesc(userId, pageable);

        return postPage.map(post -> {

            String repImageUrl = postMediaRepository.findFirstImageUrlByPostId(post.getId())
                    .orElseThrow(() -> new PostException(PostErrorCode.IMAGE_REQUIRED));

            return UserPostResponse.from(post, repImageUrl);
        });
    }
}
