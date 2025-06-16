package uniqram.c1one.user.entity;

import jakarta.persistence.*;
import uniqram.c1one.global.BaseEntity;


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

    protected Users(){}

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
