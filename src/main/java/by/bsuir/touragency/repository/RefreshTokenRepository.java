package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.RefreshToken;
import by.bsuir.touragency.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(Users user);
    void deleteByUser(Users user);
}
