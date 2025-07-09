package uniqram.c1one.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private Long parentCommentId; //대댓글인 경우
    private String content;
}
