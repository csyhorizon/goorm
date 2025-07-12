package uniqram.c1one.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.user.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    long countByBlacklistedFalse();
}