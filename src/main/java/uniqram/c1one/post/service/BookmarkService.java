package uniqram.c1one.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.post.dto.BookmarkPostResponse;
import uniqram.c1one.post.entity.Bookmark;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostMedia;
import uniqram.c1one.post.exception.PostErrorCode;
import uniqram.c1one.post.exception.PostException;
import uniqram.c1one.post.repository.BookmarkRepository;
import uniqram.c1one.post.repository.PostMediaRepository;
import uniqram.c1one.post.repository.PostRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<BookmarkPostResponse> getMyBookmarks(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);

        List<Long> postIds = bookmarks.stream()
                .map(b -> b.getPost().getId())
                .toList();

        List<PostMedia> firstImages = postMediaRepository.findFirstImagesByPostIds(postIds);

        Map<Long, String> repImageMap = firstImages.stream()
                .collect(Collectors.toMap(
                        pm -> pm.getPost().getId(),
                        PostMedia::getMediaUrl
                ));

        return bookmarks.stream()
                .map(bookmark -> {
                    Post post = bookmark.getPost();
                    String repImageUrl = repImageMap.get(post.getId());
                    return BookmarkPostResponse.of(post.getId(), repImageUrl);
                })
                .toList();
    }
}
