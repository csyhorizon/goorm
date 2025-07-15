package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.comment.dto.CommentListResponse;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.follow.repository.FollowRepository;
import uniqram.c1one.global.s3.S3Service;
import uniqram.c1one.global.service.LikeCountService;
import uniqram.c1one.post.dto.*;
import uniqram.c1one.post.entity.*;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.BookmarkRepository;
import uniqram.c1one.post.repository.PostLikeRepository;
import uniqram.c1one.post.repository.PostMediaRepository;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMediaRepository postMediaRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final LikeCountService likeCountService;
    private final S3Service s3Service;
    private final FollowRepository followRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public PostResponse createPost(Long userId, PostCreateRequest postCreateRequest) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(PostErrorCode.USER_NOT_FOUND));

        List<String> mediaUrls = postCreateRequest.getMediaUrls();

        // 이미지 필수
        if (mediaUrls == null || mediaUrls.isEmpty()) {
            throw new PostException(PostErrorCode.IMAGE_REQUIRED);
        }

        Post post = Post.of(user, postCreateRequest.getContent(), postCreateRequest.getLocation());
        postRepository.save(post);

        // 이미지 등록
        List<PostMedia> mediaList = mediaUrls.stream()
                .map(url -> PostMedia.of(post, url))
                .toList();
        postMediaRepository.saveAll(mediaList);

        return PostResponse.from(post, mediaUrls);
    }

    @Transactional(readOnly = true)
    public List<HomePostResponse> getFollowingRecentPosts(Long userId) {

        List<Long> followingIds = followRepository.findFollowerIdsByUserId(userId);

        if (followingIds.isEmpty()) {
            return List.<HomePostResponse>of();
        }

        LocalDateTime fourDaysAgo = LocalDateTime.now().minusDays(4);

        List<Post> recentPostsByUserIds = postRepository.findRecentPostsByUserIds(followingIds, fourDaysAgo, PageRequest.of(0, 5));

        List<Long> postIds = recentPostsByUserIds.stream()
                .map(Post::getId)
                .toList();

        List<PostMedia> postMediaList = postMediaRepository.findByPostIdIn(postIds);

        Map<Long, List<String>> postMediaMap = postMediaList.stream()
                .collect(Collectors.groupingBy(
                        pm -> pm.getPost().getId(),
                        Collectors.mapping(PostMedia::getMediaUrl, Collectors.toList())
                ));

        // 좋아요 개수
        Map<Long, Integer> likeCountMap = postIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        likeCountService::getPostLikeCount
                ));

        // 좋아요 누른 사람
        Map<Long, List<LikeUserDto>> likeUsersMap = postLikeRepository.findLikeUsersByPostIds(postIds).stream()
                .collect(Collectors.groupingBy(LikeUserDto::getPostId));

        // 본인 좋아요 여부
        Set<Long> likedPostIdSet = new HashSet<>(postLikeRepository.findPostIdsLikedByUser(postIds, userId));

        Set<Long> bookmarkedPostIdSet = new HashSet<>(bookmarkRepository.findPostIdsBookmarkedByUser(postIds, userId));

        // 댓글 조회
        Map<Long, List<CommentResponse>> commentMap = commentRepository.findCommentsByPostIds(postIds).stream()
                .collect(Collectors.groupingBy(CommentResponse::getPostId));

        return recentPostsByUserIds.stream().map(post -> {
            List<String> mediaUrls = postMediaMap.getOrDefault(post.getId(), List.of());
            int likeCount = likeCountMap.getOrDefault(post.getId(), 0);
            List<LikeUserDto> likeUsers = likeUsersMap.getOrDefault(post.getId(), List.of());
            boolean likedByMe = likedPostIdSet.contains(post.getId());
            boolean bookmarkedByMe = bookmarkedPostIdSet.contains(post.getId());
            List<CommentResponse> comments = commentMap.getOrDefault(post.getId(), List.of());

            return HomePostResponse.from(post, mediaUrls, likeCount, likeUsers, likedByMe, bookmarkedByMe, comments);
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<HomePostResponse> getRecommendedPosts(Long userId) {

        List<Long> excludeIds = followRepository.findFollowerIdsByUserId(userId);
        excludeIds.add(userId);

        List<Post> topLikedPosts = postRepository.findTopLikedPosts(excludeIds, Pageable.ofSize(3));

        List<Long> topLikedPostIds = topLikedPosts.stream()
                .map(Post::getId)
                .toList();

        List<Post> randomPosts = postRepository.findRandomPosts(excludeIds, topLikedPostIds, Pageable.ofSize(5));

        List<Post> combinedPosts = new ArrayList<>();
        combinedPosts.addAll(topLikedPosts);
        combinedPosts.addAll(randomPosts);

        Collections.shuffle(combinedPosts);

        List<Long> combinedPostIds = combinedPosts.stream()
                .map(Post::getId)
                .toList();

        List<PostMedia> postMediaList = postMediaRepository.findByPostIdIn(combinedPostIds);

        Map<Long, List<String>> postMediaMap = postMediaList.stream()
                .collect(Collectors.groupingBy(
                        pm -> pm.getPost().getId(),
                        Collectors.mapping(PostMedia::getMediaUrl, Collectors.toList())
                ));

        // 좋아요 개수
        Map<Long, Integer> likeCountMap = combinedPostIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        likeCountService::getPostLikeCount
                ));

        // 좋아요 누른 사람
        Map<Long, List<LikeUserDto>> likeUsersMap = postLikeRepository.findLikeUsersByPostIds(combinedPostIds).stream()
                .collect(Collectors.groupingBy(LikeUserDto::getPostId));

        // 본인 좋아요 여부
        Set<Long> likedPostIdSet = new HashSet<>(postLikeRepository.findPostIdsLikedByUser(combinedPostIds, userId));

        Set<Long> bookmarkedPostIdSet = new HashSet<>(bookmarkRepository.findPostIdsBookmarkedByUser(combinedPostIds, userId));

        // 댓글 조회
        Map<Long, List<CommentResponse>> commentMap = commentRepository.findCommentsByPostIds(combinedPostIds).stream()
                .collect(Collectors.groupingBy(CommentResponse::getPostId));

        return combinedPosts.stream().map(post -> {
            List<String> mediaUrls = postMediaMap.getOrDefault(post.getId(), List.of());
            int likeCount = likeCountMap.getOrDefault(post.getId(), 0);
            List<LikeUserDto> likeUsers = likeUsersMap.getOrDefault(post.getId(), List.of());
            boolean likedByMe = likedPostIdSet.contains(post.getId());
            boolean bookmarkedByMe = bookmarkedPostIdSet.contains(post.getId());
            List<CommentResponse> comments = commentMap.getOrDefault(post.getId(), List.of());

            return HomePostResponse.from(post, mediaUrls, likeCount, likeUsers, likedByMe, bookmarkedByMe, comments);
        }).toList();
    }

    @Transactional(readOnly = true)
    public Page<UserPostResponse> getUserPosts(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postRepository.findByUserIdOrderByIdDesc(userId, pageable);

        List<Long> postIds = postPage.stream()
                .map(Post::getId)
                .toList();

        List<PostMedia> firstImages = postMediaRepository.findFirstImagesByPostIds(postIds);
        Map<Long, String> repImageMap = firstImages.stream()
                .collect(Collectors.toMap(
                        pm -> pm.getPost().getId(),
                        PostMedia::getMediaUrl
                ));

        return postPage.map(post -> {
            String repImageUrl = repImageMap.get(post.getId());

            return UserPostResponse.from(post, repImageUrl);
        });
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        List<String> mediaUrls = postMediaRepository.findByPostIdOrderByIdAsc(postId)
                .stream().map(PostMedia::getMediaUrl)
                .toList();

        int likeCount = likeCountService.getPostLikeCount(postId);

        List<LikeUserDto> likeUsers = postLikeRepository.findLikeUsersByPostId(postId);

        boolean likedByMe = postLikeRepository.existsByPostIdAndUserId(postId, userId);

        boolean bookmarkedByMe = bookmarkRepository.existsByUserIdAndPostId(userId, postId);

        List<CommentListResponse> comments = commentRepository.findCommentsByPostId(postId);

        return PostDetailResponse.from(post, mediaUrls, likeCount, likeUsers, likedByMe, bookmarkedByMe, comments);
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest postUpdateRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new PostException(PostErrorCode.NO_AUTHORITY);
        }

        List<PostMedia> currentMedia = postMediaRepository.findByPostIdOrderByIdAsc(postId);
        int currentCount = currentMedia.size();

        List<String> remainUrls = postUpdateRequest.getRemainImageUrls() != null
                ? postUpdateRequest.getRemainImageUrls().stream()
                .filter(url -> url != null && !url.trim().isEmpty())
                .toList()
                : List.of();

        List<PostMedia> deleteMedia = currentMedia.stream()
                .filter(pm -> !remainUrls.contains(pm.getMediaUrl()))
                .toList();

        int afterDeletionCount = remainUrls.size();

        if (currentCount <= 2 && !deleteMedia.isEmpty()) {
            throw new PostException(PostErrorCode.IMAGE_MINIMUM_REQUIRED);
        }

        if (currentCount >= 3 && afterDeletionCount < 2) {
            throw new PostException(PostErrorCode.IMAGE_MINIMUM_REQUIRED);
        }

        for (PostMedia media : deleteMedia) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postMediaRepository.deleteAll(deleteMedia);

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
        for (PostMedia media : mediaList) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postMediaRepository.deleteAll(mediaList);

        List<PostLike> postLikes = postLikeRepository.findByPostId(postId);
        postLikeRepository.deleteAll(postLikes);

        List<Comment> comments = commentRepository.findByPost(post);
        commentRepository.deleteAll(comments);

        postRepository.delete(post);
    }
}
