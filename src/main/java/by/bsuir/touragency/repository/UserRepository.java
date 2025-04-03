package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);
    boolean existsByPhoneNumber(String phoneNumber);
}
