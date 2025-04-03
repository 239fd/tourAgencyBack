package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.RegistrationRequest;
import by.bsuir.touragency.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    Users registrationRequestToUser(RegistrationRequest request);
}
