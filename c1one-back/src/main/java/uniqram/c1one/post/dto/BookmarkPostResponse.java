package uniqram.c1one.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkPostResponse {

    private Long postId;
    private String representativeImageUrl;

    public static BookmarkPostResponse of(Long postId, String representativeImageUrl) {
        return BookmarkPostResponse.builder()
                .postId(postId)
                .representativeImageUrl(representativeImageUrl)
                .build();
    }
}
