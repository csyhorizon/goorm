package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.dto.*;
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
    public PostResponse createPost(Long userId, PostCreateRequest postCreateRequest) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(PostErrorCode.USER_NOT_FOUND));

        // 이미지 필수
        if (postCreateRequest.getMediaUrls() == null || postCreateRequest.getMediaUrls().isEmpty()) {
            throw new PostException(PostErrorCode.IMAGE_REQUIRED);
        }

        Post post = Post.of(user, postCreateRequest.getContent(), postCreateRequest.getLocation());
        postRepository.save(post);

        // 이미지 등록
        if (postCreateRequest.getMediaUrls() != null && !postCreateRequest.getMediaUrls().isEmpty()) {
            List<PostMedia> mediaList = postCreateRequest.getMediaUrls().stream()
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

            String repImageUrl = postMediaRepository.findFirstByPostIdOrderByIdAsc(post.getId())
                    .map(PostMedia::getMediaUrl)
                    .orElseThrow(() -> new PostException(PostErrorCode.IMAGE_REQUIRED));

            return UserPostResponse.from(post, repImageUrl);
        });
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest postUpdateRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new PostException(PostErrorCode.NO_AUTHORITY);
        }

        // 기존 이미지에서 특정 이미지 삭제
        List<PostMedia> currentMedia = postMediaRepository.findByPostIdOrderByIdAsc(postId);
        List<String> remainUrls = postUpdateRequest.getRemainImageUrls() != null ? postUpdateRequest.getRemainImageUrls() : List.of();

        List<PostMedia> deleteMedia = currentMedia.stream()
                .filter(pm -> !remainUrls.contains(pm.getMediaUrl()))
                .toList();

        postMediaRepository.deleteAll(deleteMedia);

        // 새 이미지 등록
        if (postUpdateRequest.getNewImageUrls() != null && !postUpdateRequest.getNewImageUrls().isEmpty()) {
            List<PostMedia> newMediaList = postUpdateRequest.getNewImageUrls().stream()
                    .map(url -> PostMedia.of(post, url))
                    .toList();
            postMediaRepository.saveAll(newMediaList);
        }

        // 최종 이미지 개수 확인
        int finalImageCount = postMediaRepository.findByPostIdOrderByIdAsc(postId).size();
        if (finalImageCount == 0) {
            throw new PostException(PostErrorCode.IMAGE_REQUIRED);
        }

        post.update(postUpdateRequest.getContent(), postUpdateRequest.getLocation());

        List<String> mediaUrls = postMediaRepository.findByPostIdOrderByIdAsc(postId)
                .stream()
                .map(PostMedia::getMediaUrl)
                .toList();

        return PostResponse.from(post, mediaUrls);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new PostException(PostErrorCode.NO_AUTHORITY);
        }

        List<PostMedia> mediaList = postMediaRepository.findByPostIdOrderByIdAsc(postId);
        postMediaRepository.deleteAll(mediaList);

        // TODO: 댓글, 좋아요 연관 삭제 로직 추가

        postRepository.delete(post);
    }
}
