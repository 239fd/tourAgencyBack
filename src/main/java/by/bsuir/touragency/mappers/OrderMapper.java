package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.OrderDTO;
import by.bsuir.touragency.dto.OrderSubmissionDTO;
import by.bsuir.touragency.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "createdDate", target = "createdDateTime")
    @Mapping(source = "updateStatusDate", target = "modifiedDateTime")
    @Mapping(source = "date", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "nameOfTour", target = "nameOfTour")
    @Mapping(source = "numberOfDays", target = "numberOfDays")
    @Mapping(source = "numberOfPeople", target = "numberOfPeople")
    @Mapping(source = "status", target = "status")
    OrderDTO toOrderDTO(Orders order);
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "id", ignore = true)
    Orders fromSubmissionDTO(OrderSubmissionDTO dto);
    List<OrderDTO> toDtoList(List<Orders> orders);

}
