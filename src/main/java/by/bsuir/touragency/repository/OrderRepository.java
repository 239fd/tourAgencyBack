package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Orders;
import by.bsuir.touragency.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAll();
    List<Orders> findAllByUsers(Users users);
    int countByUsersId(Long userId);
    List<Orders> findAllByUsersId(Long userId);
    List<Orders> findAllByCreatedDateBetween(Instant start, Instant end);
    @Query("SELECT o FROM Orders o JOIN o.users u " +
            "WHERE CONCAT(u.surname, ' ', u.name, ' ', COALESCE(u.patronymic, '')) LIKE %:query% " +
            "OR o.nameOfTour LIKE %:query%")
    List<Orders> searchOrders(@Param("query") String query);
}
