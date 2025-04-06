package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<Languages, Integer> {
    Set<Languages> findByLanguage(String language);

}
