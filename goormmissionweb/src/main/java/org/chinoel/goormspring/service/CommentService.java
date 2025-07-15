package org.chinoel.goormspring.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.entity.Comments;
import org.chinoel.goormspring.entity.Post;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comments> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdAndIsDeletedFalseOrderByCreatedAtAsc(postId);
    }

    public Comments addComment(Post post, User user, String content) {
        Comments comment = Comments.addComment(post, user, content);
        commentRepository.save(comment);

        return comment;
    }
}
