package uniqram.c1one.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private Long userId;
    private String userName;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long parentCommentId;
}
