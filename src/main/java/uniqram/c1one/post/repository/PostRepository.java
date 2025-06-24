package uniqram.c1one.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query("SELECT p FROM Post p JOIN FETCH p.mediaList WHERE p.id = :id")
//    Optional<Post> findByIdWithMedia(@Param("id") Long id);
}
