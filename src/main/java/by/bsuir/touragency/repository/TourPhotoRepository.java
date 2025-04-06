package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.TourPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourPhotoRepository extends JpaRepository<TourPhoto, Long> {
    List<TourPhoto> findByTourId(Long tourId);
}
