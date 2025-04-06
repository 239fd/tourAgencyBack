package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Orders;
import by.bsuir.touragency.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAll();
    List<Orders> findAllByUsers(Users users);
}
