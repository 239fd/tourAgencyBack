package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.UserDetailsDTO;
import by.bsuir.touragency.dto.UserProfileDTO;
import by.bsuir.touragency.dto.UserDTO;
import by.bsuir.touragency.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileDTO toProfileDTO(Users user);

    @Mapping(target = "fullName", expression = "java(user.getSurname() + \" \" + user.getName() + (user.getPatronymic() != null ? \" \" + user.getPatronymic() : \"\"))")
    UserDTO toUserDTO(Users user);

    @Mapping(target = "fullName", expression = "java(user.getSurname() + \" \" + user.getName() + (user.getPatronymic() != null ? \" \" + user.getPatronymic() : \"\"))")
    @Mapping(target = "birthday", expression = "java(mapInstantToLocalDate(user.getBirthday()))")
    UserDetailsDTO toUserDetailsDTO(Users user);

    default LocalDate mapInstantToLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
