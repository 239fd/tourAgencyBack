package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.entity.Tours;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourMapper {
    TourDTO toTourDTO(Tours tours);
    List<TourDTO> toDtoList(List<Tours> tours);
}
