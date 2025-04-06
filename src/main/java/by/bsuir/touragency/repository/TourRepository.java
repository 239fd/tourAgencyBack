package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Tours;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tours, Long> {
    List<Tours> findAll();
    @Query("SELECT t FROM Tours t WHERE t.id = :id AND FUNCTION('DATE', t.startDate) = :startDate")
    Optional<Tours> findByIdAndStartDate(@Param("id") Long id, @Param("startDate") LocalDate startDate);
    List<Tours> findAllByName(String name);
    List<Tours> findAll(Specification<Tours> spec, Sort sort);
}
