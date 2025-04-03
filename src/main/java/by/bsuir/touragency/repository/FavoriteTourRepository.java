package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.UsersFavoriteTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteTourRepository extends JpaRepository<UsersFavoriteTour, Integer > {}
