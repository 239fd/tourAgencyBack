package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.OrderHasLanguage;
import by.bsuir.touragency.entity.OrderHasLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHasLanguageRepository extends JpaRepository<OrderHasLanguage, OrderHasLanguageId> {
}
