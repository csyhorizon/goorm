package uniqram.c1one.user.entity;

import jakarta.persistence.*;
import uniqram.c1one.comment.entity.Comment;
import uniqram.c1one.global.BaseEntity;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected Users(){}

    public Users(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

}
