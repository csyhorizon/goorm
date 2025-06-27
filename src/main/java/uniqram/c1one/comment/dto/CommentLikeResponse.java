package uniqram.c1one.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentLikeResponse {
    private Long commentId;
    private boolean liked;
    private int likeCount;
}
