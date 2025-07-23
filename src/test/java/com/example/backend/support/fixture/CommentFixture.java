package com.example.backend.support.fixture;

import com.example.backend.domain.comment.entity.Comment;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.post.entity.Post;
import lombok.Getter;

@Getter
public enum CommentFixture {
    댓글( "댓글 테스트"),
    대댓글("대댓글 테스트");

    private String content;

    CommentFixture(String content) {
        this.content = content;
    }

    public static Comment 댓글(Member member, Post post){
        return new Comment(member, post, null, 댓글.content);
    }

    public static Comment 대댓글(Member member, Post post, Comment parentComment) {
        return new Comment(member, post, parentComment, 대댓글.content);
    }

}
