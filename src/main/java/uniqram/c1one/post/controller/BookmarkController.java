package uniqram.c1one.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.post.dto.BookmarkPostResponse;
import uniqram.c1one.post.dto.BookmarkRequest;
import uniqram.c1one.post.exception.BookmarkSuccessCode;
import uniqram.c1one.post.service.BookmarkService;
import uniqram.c1one.security.adapter.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Boolean>> bookmark(
            @RequestBody @Valid BookmarkRequest bookmarkRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        boolean isBookmarked = bookmarkService.bookmark(userId, bookmarkRequest.getPostId());
        return ResponseEntity.ok(SuccessResponse.of(BookmarkSuccessCode.BOOKMARK_TOGGLED, isBookmarked));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<BookmarkPostResponse>>> getMyBookmarks(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        List<BookmarkPostResponse> bookmarks = bookmarkService.getMyBookmarks(userId);
        return ResponseEntity.ok(SuccessResponse.of(BookmarkSuccessCode.BOOKMARK_LIST_LOADED, bookmarks));
    }
}
