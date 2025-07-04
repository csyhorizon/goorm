package uniqram.c1one.user.entity;

import jakarta.persistence.*;
import lombok.*;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.global.BaseEntity;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Users extends BaseEntity {
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

    public Users(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

}
