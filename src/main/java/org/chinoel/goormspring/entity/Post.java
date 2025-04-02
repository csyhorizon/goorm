package org.chinoel.goormspring.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chinoel.goormspring.dto.request.PostAddDto;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Size(max = 255)
    @NotNull
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @ColumnDefault("0")
    @Column(name = "VIEW_COUNT")
    private Integer viewCount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @ColumnDefault("0")
    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Size(max = 50)
    @Column(name = "ACCESS_LEVEL", length = 50)
    private String accessLevel;

    public static Post addPost(PostAddDto post, User user) {
        return Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(user)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .isDeleted(false)
                .viewCount(0)
                .accessLevel(post.isSecret() ? "PRIVATE" : "PUBLIC")
                .build();
    }
}
