package uniqram.c1one.comment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import uniqram.c1one.comment.dto.CommentCreateRequest;
import uniqram.c1one.comment.dto.CommentResponse;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.comment.repository.CommentRepository;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.repository.PostRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

//    @Test
//    void createComment() {
//        //given
//        Long userId = 1L;
//        Long postId = 2L;
//        String content = "This is a comment";
//        CommentCreateRequest commentCreateRequest = new CommentCreateRequest(userId, postId, null, content);
//
//        Users user = Users.builder().id(userId).username("tested").build();
//        Post post = Post.builder().id(postId).content("test post").user(user).build();
//        Comment comment = Comment.builder().id(10L).user(user).post(post).content(content).build();
//
//        given(userRepository.findById(userId)).willReturn(Optional.of(user));
//        given(postRepository.findById(postId)).willReturn(Optional.of(post));
//        given(commentRepository.save(any(Comment.class))).willReturn(comment);
//
//        CommentResponse response = commentService.createComment(userId, commentCreateRequest);
//
//        assertThat(response.getCommentId()).isEqualTo(10L);
//        assertThat(response.getUserId()).isEqualTo(userId);
//        assertThat(response.getContent()).isEqualTo(content);
//        assertThat(response.getParentCommentId()).isNull();
//    }

}
