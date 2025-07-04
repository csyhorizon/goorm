/**
package uniqram.c1one.comment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uniqram.c1one.comment.dto.CommentLikeResponse;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.comment.entity.CommentLike;
import uniqram.c1one.comment.repository.CommentLikeRepository;
import uniqram.c1one.comment.repository.CommentRepository;

import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentLikeServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentLikeRepository commentLikeRepository;

    @InjectMocks
    private CommentLikeService commentLikeService;

    @Test
    void commentLike() {

        //given
        Long userId = 1L;
        Long commentId = 2L;

        Users user = Users.builder().id(userId).username("tester").build();
        Comment comment = Comment.builder().id(commentId).user(user).content("test01").build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        given(commentLikeRepository.findByUserAndComment(user, comment)).willReturn(Optional.empty());
        given(commentLikeRepository.countByComment(comment)).willReturn(1);

        //when
        CommentLikeResponse likeResponse = commentLikeService.likeComment(userId, commentId);

        //then
        assertThat(likeResponse.isLiked()).isTrue();
        assertThat(likeResponse.getCommentId()).isEqualTo(commentId);
        assertThat(likeResponse.getLikeCount()).isEqualTo(1);

        //save가 1번 호출되는가
        then(commentLikeRepository).should(times(1)).save(any(CommentLike.class));
    }

    @Test
    void commentNotLike() {

        //given
        Long userId = 1L;
        Long commentId = 2L;

        Users user = Users.builder().id(userId).username("tester").build();
        Comment comment = Comment.builder().id(commentId).user(user).content("test02").build();
        CommentLike existingLike = CommentLike.builder().user(user).comment(comment).build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        given(commentLikeRepository.findByUserAndComment(user, comment)).willReturn(Optional.of(existingLike));
        given(commentLikeRepository.countByComment(comment)).willReturn(0);

        //when
        CommentLikeResponse likeResponse = commentLikeService.likeComment(userId, commentId);

        //then
        assertThat(likeResponse.isLiked()).isFalse();
        assertThat(likeResponse.getCommentId()).isEqualTo(commentId);
        assertThat(likeResponse.getLikeCount()).isEqualTo(0);

        //delete가 1번 호출되는가
        then(commentLikeRepository).should(times(1)).delete(existingLike);
    }



}
*/