package com.example.backend.domain.post.entity;

import com.example.backend.domain.global.BaseEntity;
import com.example.backend.domain.store.entity.Store;
import com.example.backend.domain.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(length = 4096)
    private String images;

    private String location;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostMedia> mediaList = new ArrayList<>();

    private Post(Users user, Store store, String location, String title, String content) {
        this.user = user;
        this.store = store;
        this.location = location;
        this.title = title;
        this.content = content;
    }



    public static Post of(Users user,Store store, String location, String title, String content) {
        return new Post(user, store, location, title, content);
    }

    public void update(String title, String content, String location) {
        this.title = title;
        this.content = content;
        this.location = location;
    }

}
