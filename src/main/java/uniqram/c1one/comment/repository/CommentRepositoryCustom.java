package uniqram.c1one.comment.repository;

import uniqram.c1one.comment.dto.CommentListResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentListResponse> findCommentsByPostId(Long postId);
}
