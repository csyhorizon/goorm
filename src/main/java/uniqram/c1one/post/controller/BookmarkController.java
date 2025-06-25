package uniqram.c1one.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.post.dto.BookmarkPostResponse;
import uniqram.c1one.post.dto.BookmarkRequest;
import uniqram.c1one.post.exception.BookmarkSuccessCode;
import uniqram.c1one.post.service.BookmarkService;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Boolean>> bookmark(
            @RequestBody @Valid BookmarkRequest bookmarkRequest
    ) {
        boolean isBookmarked = bookmarkService.bookmark(bookmarkRequest.getUserId(), bookmarkRequest.getPostId());
        return ResponseEntity.ok(SuccessResponse.of(BookmarkSuccessCode.BOOKMARK_TOGGLED, isBookmarked));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<BookmarkPostResponse>>> getMyBookmarks(
            @RequestParam Long userId
    ) {
        List<BookmarkPostResponse> bookmarks = bookmarkService.getMyBookmarks(userId);
        return ResponseEntity.ok(SuccessResponse.of(BookmarkSuccessCode.BOOKMARK_LIST_LOADED, bookmarks));
    }
}
