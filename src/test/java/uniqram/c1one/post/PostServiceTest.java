package uniqram.c1one.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.follow.repository.FollowRepository;
import uniqram.c1one.global.s3.S3Service;
import uniqram.c1one.global.service.LikeCountService;
import uniqram.c1one.post.dto.*;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostMedia;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.PostLikeRepository;
import uniqram.c1one.post.repository.PostMediaRepository;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.post.service.PostService;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMediaRepository postMediaRepository;

    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private LikeCountService likeCountService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private S3Service s3Service;

    @Test
    @DisplayName("게시물 생성 성공")
    void create_post_success() {
        // given
        Long userId = 1L;
        Users user = Users.builder().
                id(userId)
                .username("테스트유저")
                .build();

        PostCreateRequest request = PostCreateRequest.builder()
                .content("테스트")
                .location("서울")
                .mediaUrls(List.of("url1", "url2"))
                .build();

        Post savedPost = Post.of(user, request.getContent(), request.getLocation());

        ReflectionTestUtils.setField(savedPost, "id", 1L);

        List<PostMedia> savedMediaList = List.of(
                PostMedia.of(savedPost, "url1"),
                PostMedia.of(savedPost, "url2")
        );

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post argument = invocation.getArgument(0);
            ReflectionTestUtils.setField(argument, "id", 1L); // id 강제 주입
            return argument;
        });

        // then
        PostResponse response = postService.createPost(userId, request);

        assertEquals(savedPost.getId(), response.getPostId());
        assertEquals(request.getContent(), response.getContent());
        assertEquals(request.getLocation(), response.getLocation());
        assertEquals(List.of("url1", "url2"), response.getMediaUrls());

        verify(userRepository).findById(userId);
        verify(postRepository).save(any(Post.class));
        verify(postMediaRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("게시물 생성 실패 - 이미지 없음")
    void create_post_fail_noImage() {
        // given
        Long userId = 1L;
        Users user = Users.builder().id(userId).build();

        PostCreateRequest request = PostCreateRequest.builder()
                .content("테스트 게시글")
                .location("서울")
                .mediaUrls(Collections.emptyList())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when & then
        assertThrows(PostException.class, () -> postService.createPost(userId, request));
        verify(userRepository).findById(userId);
        verify(postRepository, never()).save(any());
    }

    @Test
    @DisplayName("팔로잉 중인 유저의 게시물 목록 조회 성공")
    void getFollowingRecentPosts_success() {
        // given
        Long userId = 1L;

        List<Long> followingIds = List.of(2L, 3L);

        List<Post> postList = List.of(
                Post.of(Users.builder().id(2L).build(), "내용1", "서울"),
                Post.of(Users.builder().id(3L).build(), "내용2", "부산")
        );
        ReflectionTestUtils.setField(postList.get(0), "id", 1L);
        ReflectionTestUtils.setField(postList.get(1), "id", 2L);

        List<PostMedia> mediaList = List.of(
                PostMedia.of(postList.get(0), "url1"),
                PostMedia.of(postList.get(1), "url2")
        );

        List<LikeUserDto> likeUserList = List.of(
                new LikeUserDto(1L, 10L, "userA"),
                new LikeUserDto(1L, 11L, "userB"),
                new LikeUserDto(2L, 12L, "userC")
        );

        List<Long> likedPostIds = List.of(1L);

        when(followRepository.findFollowerIdsByUserId(userId)).thenReturn(followingIds);
        when(postRepository.findRecentPostsByUserIds(eq(followingIds), any(), any())).thenReturn(postList);
        when(postMediaRepository.findByPostIdIn(anyList())).thenReturn(mediaList);
        when(likeCountService.getPostLikeCount(1L)).thenReturn(3);
        when(likeCountService.getPostLikeCount(2L)).thenReturn(5);
        when(postLikeRepository.findLikeUsersByPostIds(anyList())).thenReturn(likeUserList);
        when(postLikeRepository.findPostIdsLikedByUser(anyList(), eq(userId))).thenReturn(likedPostIds);
        when(commentRepository.findCommentsByPostIds(anyList())).thenReturn(List.of());

        // when
        List<HomePostResponse> responses = postService.getFollowingRecentPosts(userId);

        // then
        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).getPostId());
        assertEquals(3, responses.get(0).getLikeCount());
        assertTrue(responses.get(0).isLikedByMe());
        assertEquals(List.of("url1"), responses.get(0).getMediaUrls());
        assertEquals(2, responses.get(0).getLikeUsers().size());

        assertEquals(2L, responses.get(1).getPostId());
        assertEquals(5, responses.get(1).getLikeCount());
        assertFalse(responses.get(1).isLikedByMe());
        assertEquals(List.of("url2"), responses.get(1).getMediaUrls());
        assertEquals(1, responses.get(1).getLikeUsers().size());

        verify(followRepository).findFollowerIdsByUserId(userId);
        verify(postRepository).findRecentPostsByUserIds(eq(followingIds), any(), any());
        verify(postMediaRepository).findByPostIdIn(anyList());
        verify(postLikeRepository).findLikeUsersByPostIds(anyList());
        verify(postLikeRepository).findPostIdsLikedByUser(anyList(), eq(userId));
    }

    @Test
    @DisplayName("팔로잉 중인 유저의 게시물 목록 조회 실패 - 게시물이 없는 경우 빈 리스트 반환")
    void getFollowingRecentPosts_empty() {
        // given
        Long userId = 1L;

        when(followRepository.findFollowerIdsByUserId(userId)).thenReturn(List.of(2L, 3L));
        when(postRepository.findRecentPostsByUserIds(anyList(), any(), any())).thenReturn(Collections.emptyList());

        // when
        List<HomePostResponse> responses = postService.getFollowingRecentPosts(userId);

        // then
        assertEquals(0, responses.size());

        verify(followRepository).findFollowerIdsByUserId(userId);
        verify(postRepository).findRecentPostsByUserIds(anyList(), any(), any());
    }

    @Test
    @DisplayName("추천 게시물 조회 성공")
    void getRecommendedPosts_success() {
        // given
        Long userId = 1L;
        List<Long> excludeIds = List.of(2L, 3L, userId);

        Users writer = Users.builder().id(4L).build();
        Post post = Post.of(writer, "추천글1", "서울");
        ReflectionTestUtils.setField(post, "id", 10L);

        Page<Post> recommendedPostPage = new PageImpl<>(List.of(post), PageRequest.of(0, 5), 1);

        when(followRepository.findFollowerIdsByUserId(userId)).thenReturn(new ArrayList<>(List.of(2L, 3L)));
        when(postMediaRepository.findByPostIdIn(anyList())).thenReturn(List.of());
        when(likeCountService.getPostLikeCount(10L)).thenReturn(0);
        when(postLikeRepository.findLikeUsersByPostIds(List.of(10L))).thenReturn(List.of());
        when(postLikeRepository.findPostIdsLikedByUser(List.of(10L), userId)).thenReturn(List.of());
        when(commentRepository.findCommentsByPostIds(List.of(10L))).thenReturn(List.of());
        when(postRepository.findTopLikedPosts(anyList(), any(Pageable.class))).thenReturn(List.of(post));
        when(postRepository.findRandomPosts(anyList(), anyList(), any(Pageable.class))).thenReturn(List.of());


        // when
        List<HomePostResponse> responseList = postService.getRecommendedPosts(userId);

        // then
        assertEquals(1, responseList.size());
        assertEquals(10L, responseList.get(0).getPostId());
    }


    @Test
    @DisplayName("추천 게시물 조회 실패 - 추천 게시물 없는 경우 빈 리스트 반환")
    void getRecommendedPosts_fail_empty() {
        // given
        Long userId = 1L;
        List<Long> excludeIds = List.of(2L, 3L, userId);

        Page<Post> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 5), 0);

        when(followRepository.findFollowerIdsByUserId(userId)).thenReturn(new ArrayList<>(List.of(2L, 3L)));

        // when
        List<HomePostResponse> responseList = postService.getRecommendedPosts(userId);

        // then
        assertTrue(responseList.isEmpty());
    }

    @Test
    @DisplayName("게시물 상세 조회 성공")
    void getPostDetail_success() {
        Long userId = 1L;
        Long postId = 2L;
        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");
        ReflectionTestUtils.setField(post, "id", postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMediaRepository.findByPostIdOrderByIdAsc(postId)).thenReturn(List.of(
                PostMedia.of(post, "url1"), PostMedia.of(post, "url2")
        ));
        when(likeCountService.getPostLikeCount(postId)).thenReturn(3);
        when(postLikeRepository.findLikeUsersByPostId(postId)).thenReturn(List.of());
        when(postLikeRepository.existsByPostIdAndUserId(postId, userId)).thenReturn(true);
        when(commentRepository.findCommentsByPostId(postId)).thenReturn(List.of());

        PostDetailResponse response = postService.getPostDetail(userId, postId);

        assertEquals(postId, response.getPostId());
        assertEquals(3, response.getLikeCount());
        assertTrue(response.isLikedByMe());
        assertEquals(List.of("url1", "url2"), response.getMediaUrls());
    }

    @Test
    @DisplayName("상세 조회 실패 - 존재하지 않는 게시물")
    void getPostDetail_fail_postNotFound() {
        Long userId = 1L;
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostException.class, () -> postService.getPostDetail(userId, postId));

        verify(postRepository).findById(postId);
    }

    @Test
    @DisplayName("게시물 수정 성공 - 이미지 3개 이상 유지하고 일부 삭제")
    void updatePost_success() {
        Long userId = 1L;
        Long postId = 2L;
        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");
        ReflectionTestUtils.setField(post, "id", postId);

        List<PostMedia> currentMedia = List.of(
                PostMedia.of(post, "url1"),
                PostMedia.of(post, "url2"),
                PostMedia.of(post, "url3"),
                PostMedia.of(post, "url4")
        );

        PostUpdateRequest request = PostUpdateRequest.builder()
                .content("수정된 내용")
                .location("부산")
                .remainImageUrls(List.of("url1", "url2", "url3")) // url4 삭제 요청
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMediaRepository.findByPostIdOrderByIdAsc(postId)).thenReturn(currentMedia)
                .thenReturn(List.of(
                        PostMedia.of(post, "url1"),
                        PostMedia.of(post, "url2"),
                        PostMedia.of(post, "url3")
                ));

        PostResponse response = postService.updatePost(userId, postId, request);

        assertEquals(postId, response.getPostId());
        assertEquals(List.of("url1", "url2", "url3"), response.getMediaUrls());
    }

    @Test
    @DisplayName("게시물 수정 실패 - 권한 없음")
    void updatePost_fail_noAuthority() {
        Long userId = 2L;
        Long postId = 1L;

        Users owner = Users.builder().id(1L).build();
        Post post = Post.of(owner, "내용", "서울");
        ReflectionTestUtils.setField(post, "id", postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostUpdateRequest request = PostUpdateRequest.builder()
                .content("수정")
                .location("부산")
                .remainImageUrls(List.of("url1", "url2", "url3"))
                .build();

        assertThrows(PostException.class, () -> postService.updatePost(userId, postId, request));

        verify(postRepository).findById(postId);
    }
    @Test
    @DisplayName("게시물 수정 실패 - 최소 이미지 수 미달 (삭제 후 1개 남음)")
    void updatePost_fail_minImageRequired() {
        Long userId = 1L;
        Long postId = 2L;
        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");
        ReflectionTestUtils.setField(post, "id", postId);

        List<PostMedia> currentMedia = List.of(
                PostMedia.of(post, "url1"),
                PostMedia.of(post, "url2"),
                PostMedia.of(post, "url3")
        );

        PostUpdateRequest request = PostUpdateRequest.builder()
                .content("수정된 내용")
                .location("부산")
                .remainImageUrls(List.of("url1"))
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMediaRepository.findByPostIdOrderByIdAsc(postId)).thenReturn(currentMedia);

        assertThrows(PostException.class, () -> postService.updatePost(userId, postId, request));
    }

    @Test
    @DisplayName("게시물 삭제 성공")
    void deletePost_success() {
        Long userId = 1L;
        Long postId = 2L;
        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");
        ReflectionTestUtils.setField(post, "id", postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findByPost(any())).thenReturn(List.of());

        postService.deletePost(userId, postId);

        verify(postMediaRepository).deleteAll(anyList());
        verify(postRepository).delete(post);
    }

    @Test
    @DisplayName("게시물 삭제 실패 - 권한 없음")
    void deletePost_fail_noAuthority() {
        Long userId = 2L;
        Long postId = 1L;

        Users owner = Users.builder().id(1L).build();
        Post post = Post.of(owner, "내용", "서울");
        ReflectionTestUtils.setField(post, "id", postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        assertThrows(PostException.class, () -> postService.deletePost(userId, postId));

        verify(postRepository).findById(postId);
    }
}