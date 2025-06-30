package uniqram.c1one.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uniqram.c1one.post.dto.BookmarkPostResponse;
import uniqram.c1one.post.entity.Bookmark;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostMedia;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.BookmarkRepository;
import uniqram.c1one.post.repository.PostMediaRepository;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.post.service.BookmarkService;
import uniqram.c1one.user.entity.Users;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMediaRepository postMediaRepository;

    @Test
    @DisplayName("북마크 등록 성공")
    void bookmark_register_success() {
        Long userId = 1L;
        Long postId = 1L;
        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(bookmarkRepository.existsByUserIdAndPostId(userId, postId)).thenReturn(false);

        boolean result = bookmarkService.bookmark(userId, postId);

        assertTrue(result);
        verify(bookmarkRepository).save(any(Bookmark.class));
    }

    @Test
    @DisplayName("북마크 해제 성공")
    void bookmark_cancel_success() {
        Long userId = 1L;
        Long postId = 1L;
        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(bookmarkRepository.existsByUserIdAndPostId(userId, postId)).thenReturn(true);

        boolean result = bookmarkService.bookmark(userId, postId);

        assertFalse(result);
        verify(bookmarkRepository).deleteByUserIdAndPostId(userId, postId);
    }

    @Test
    @DisplayName("내 북마크 목록 조회 성공")
    void getMyBookmarks_success() {
        Long userId = 1L;
        Long postId = 1L;

        Users user = Users.builder().id(userId).build();
        Post post = Post.of(user, "내용", "서울");
        Bookmark bookmark = Bookmark.of(userId, post);
        PostMedia postMedia = PostMedia.of(post, "https://example.com/image.jpg");
        ReflectionTestUtils.setField(post, "id", 1L);

        when(bookmarkRepository.findByUserId(userId)).thenReturn(List.of(bookmark));
        when(postMediaRepository.findFirstImagesByPostIds(List.of(postId))).thenReturn(List.of(postMedia));

        List<BookmarkPostResponse> responses = bookmarkService.getMyBookmarks(userId);

        assertEquals(1, responses.size());
        assertEquals(postId, responses.get(0).getPostId());
        assertEquals(postMedia.getMediaUrl(), responses.get(0).getRepresentativeImageUrl());
    }

    @Test
    @DisplayName("북마크 실패 - 존재하지 않는 게시물")
    void bookmark_fail_postNotFound() {
        Long userId = 1L;
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostException.class, () -> bookmarkService.bookmark(userId, postId));

        verify(bookmarkRepository, never()).save(any());
    }
}
