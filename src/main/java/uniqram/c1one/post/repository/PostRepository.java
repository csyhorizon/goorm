package uniqram.c1one.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqram.c1one.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 게시글을 최신순 조회(홈화면용)
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    // 특정 사용자의 게시글 리스트
    Page<Post> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
