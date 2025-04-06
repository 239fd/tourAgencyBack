package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.UserProfileDTO;
import by.bsuir.touragency.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileDTO toProfileDTO(Users user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "usersFavoriteTours", ignore = true)
    void updateUserFromDto(UserProfileDTO dto, @MappingTarget Users user);


}
