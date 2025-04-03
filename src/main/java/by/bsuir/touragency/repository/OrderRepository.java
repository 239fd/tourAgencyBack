package by.bsuir.touragency.repository;

import by.bsuir.touragency.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAll();

}
