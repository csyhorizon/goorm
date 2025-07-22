package com.example.backend.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(length = 1024, nullable = false)
    private String mediaUrl;

    private PostMedia(Post post, String mediaUrl) {
        this.post = post;
        this.mediaUrl = mediaUrl;
    }

    public static PostMedia of(Post post, String mediaUrl) {
        return new PostMedia(post, mediaUrl);
    }
}
