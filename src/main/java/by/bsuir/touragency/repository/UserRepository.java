package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Enum.Role;
import by.bsuir.touragency.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    List<Users> findAllByRole(Role role);
    @Query("SELECT u FROM Users u WHERE CONCAT(u.surname, ' ', u.name, ' ', u.patronymic) LIKE %:query%")
    List<Users> searchByFullName(@Param("query") String query);
    long countByRole(Role role);
    long countByRoleAndGender(Role role, String gender);

}
