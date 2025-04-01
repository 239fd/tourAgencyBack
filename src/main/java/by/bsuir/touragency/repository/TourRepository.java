package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Tours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tours, Long> {
    List<Tours> findAll();
}
