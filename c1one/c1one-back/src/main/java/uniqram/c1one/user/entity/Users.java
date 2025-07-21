package uniqram.c1one.user.entity;

import jakarta.persistence.*;
import lombok.*;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.global.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Users extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    @Builder.Default
    private boolean blacklisted = false;

    public Users(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.blacklisted = false;
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }
}
