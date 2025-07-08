package uniqram.c1one.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniqram.c1one.security.jwt.entity.RefreshToken;
import uniqram.c1one.user.entity.Users;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(Users user);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(Users user);
}