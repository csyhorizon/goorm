package uniqram.c1one.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import uniqram.c1one.comment.dto.CommentListResponse;
import uniqram.c1one.comment.entity.QComment;
import uniqram.c1one.comment.entity.QCommentLike;
import uniqram.c1one.post.entity.QPost;
import uniqram.c1one.user.entity.QUsers;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<CommentListResponse> findCommentsByPostId(Long postId) {
        QComment comment = QComment.comment;
        QUsers users = QUsers.users;

        return queryFactory
                .select(Projections.constructor(
                        CommentListResponse.class,
                        comment.id,
                        users.id,
                        users.username,
                        comment.content,
                        Expressions.asNumber(0),
                        comment.createdAt,
                        comment.parentComment.id
                ))
                .from(comment)
                .join(comment.user, users)
                .where(comment.post.id.eq(postId))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }
}
