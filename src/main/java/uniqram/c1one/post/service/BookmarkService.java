package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.entity.Bookmark;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.BookmarkRepository;
import uniqram.c1one.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

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
}
