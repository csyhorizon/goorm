package uniqram.c1one.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.Post;
import uniqram.c1one.post.entity.PostLike;
import uniqram.c1one.user.entity.Users;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPost(Post post);
    boolean existsByUserAndPost(Users user, Post post);
    Optional<PostLike> findByUserAndPost(Users user, Post post);
}
