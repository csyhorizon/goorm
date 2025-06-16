package uniqram.c1one.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    protected Users(){}

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
