package uniqram.c1one.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {

    private Long commentId;
    private Long userId;
    private String userName;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private Long parentCommentId;
//    private Long postId;
}
