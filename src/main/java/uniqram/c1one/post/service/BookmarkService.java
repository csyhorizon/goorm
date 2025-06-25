package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.dto.BookmarkPostResponse;
import uniqram.c1one.post.entity.Bookmark;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.BookmarkRepository;
import uniqram.c1one.post.repository.PostMediaRepository;
import uniqram.c1one.post.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;

    @Transactional
    public boolean bookmark(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (bookmarkRepository.existsByUserIdAndPostId(userId, postId)) {
            bookmarkRepository.deleteByUserIdAndPostId(userId, postId);
            return false; // 해제
        } else {
            Bookmark bookmark = Bookmark.of(userId, post);
            bookmarkRepository.save(bookmark);
            return true; // 등록
        }
    }

    @Transactional(readOnly = true) // TODO: 북마크와 게시글(Post) 간 N+1 문제
    public List<BookmarkPostResponse> getMyBookmarks(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);

        return bookmarks.stream()
                .map(bookmark -> {
                    Post post = bookmark.getPost();
                    String repImageUrl = postMediaRepository.findFirstByPostIdOrderByIdAsc(post.getId())
                            .orElseThrow(() -> new IllegalStateException("대표 이미지가 존재하지 않습니다."))
                            .getMediaUrl();
                    return BookmarkPostResponse.of(post.getId(), repImageUrl);
                })
                .toList();
    }
}
