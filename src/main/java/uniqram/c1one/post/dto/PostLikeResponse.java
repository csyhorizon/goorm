package uniqram.c1one.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostLikeResponse {
    private Long postId;
    private boolean liked;
    private int likeCount;
}
