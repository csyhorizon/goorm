package com.example.backend.domain.comment.entity;

import com.example.backend.domain.global.BaseEntity;
import com.example.backend.domain.member.entity.Member;
import com.example.backend.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> childrenComment = new ArrayList<>();

    public Comment(Member member, Post post, Comment parentComment, String content) {
        this.member = member;
        this.post = post;
        this.parentComment = parentComment;
        this.content = content;
    }

    public static Comment of(Member member, Post post, Comment parentComment, String content) {
        return new Comment(member, post, parentComment, content);
    }

    public void update(String content) {
        this.content = content;
    }

    public Long getOwnerId() {
        return member.getId();
    }
}
