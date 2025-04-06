package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.OrderDTO;
import by.bsuir.touragency.dto.OrderFormDataDTO;
import by.bsuir.touragency.dto.OrderSubmissionDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderFormDataDTO getOrderFormData(String email, Long tourId, LocalDate startDate);
    OrderDTO submitOrder(String email, OrderSubmissionDTO request);
    List<OrderDTO> getOrdersByUser(String email);
    List<OrderDTO> searchOrders(String query);
    OrderDTO updateOrderStatus(Long orderId, String newStatus);
}
