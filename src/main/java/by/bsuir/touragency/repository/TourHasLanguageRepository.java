package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.TourHasLanguage;
import by.bsuir.touragency.entity.TourHasLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourHasLanguageRepository extends JpaRepository<TourHasLanguage, TourHasLanguageId> {
}
