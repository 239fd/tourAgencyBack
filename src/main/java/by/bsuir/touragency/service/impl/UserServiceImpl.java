package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.BalanceDTO;
import by.bsuir.touragency.dto.UserProfileDTO;
import by.bsuir.touragency.entity.Users;
import by.bsuir.touragency.exceptions.UserException;
import by.bsuir.touragency.mappers.UserMapper;
import by.bsuir.touragency.repository.UserRepository;
import by.bsuir.touragency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileDTO getProfile(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Пользователь не найден"));

        UserProfileDTO dto = userMapper.toProfileDTO(user);
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPatronymic(user.getPatronymic());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPassportSeries(user.getPassportSeries());
        dto.setPassportNumber(user.getPassportNumber());
        dto.setBalance(user.getBalance());
        return dto;
    }

    @Override
    public void updateProfile(String email, UserProfileDTO dto) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Пользователь не найден"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPatronymic(dto.getPatronymic());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassportSeries(dto.getPassportSeries());
        user.setPassportNumber(dto.getPassportNumber());
        userRepository.save(user);
    }

    @Override
    public BalanceDTO topUpBalance(String email, Double amount) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        return BalanceDTO.builder().balance(user.getBalance()).build();
    }
}