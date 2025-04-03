package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.OrderDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.entity.Orders;
import by.bsuir.touragency.entity.Tours;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(Orders order);
    Orders toOrders(OrderDTO orderDTO);
    List<OrderDTO> toDtoList(List<Orders> orders);

}
