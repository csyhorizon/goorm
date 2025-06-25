package uniqram.c1one.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.global.success.SuccessResponse;
import uniqram.c1one.post.dto.BookmarkRequest;
import uniqram.c1one.post.exception.BookmarkSuccessCode;
import uniqram.c1one.post.service.BookmarkService;

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
}
