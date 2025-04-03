package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.OrderDTO;
import by.bsuir.touragency.entity.Orders;
import by.bsuir.touragency.mappers.OrderMapper;
import by.bsuir.touragency.repository.OrderRepository;
import by.bsuir.touragency.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    /*
    TODO
    1) Разобраться с тем то ли возвращает
    2) Посмотреть БД
    3) Израбнное
     */
}
