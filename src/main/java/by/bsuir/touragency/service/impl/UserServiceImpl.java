package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.*;
import by.bsuir.touragency.entity.Enum.Role;
import by.bsuir.touragency.entity.Orders;
import by.bsuir.touragency.entity.Users;
import by.bsuir.touragency.exceptions.UserException;
import by.bsuir.touragency.exceptions.UserNotFoundException;
import by.bsuir.touragency.mappers.OrderMapper;
import by.bsuir.touragency.mappers.UserMapper;
import by.bsuir.touragency.repository.OrderRepository;
import by.bsuir.touragency.repository.UserRepository;
import by.bsuir.touragency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

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

    @Override
    public List<UserDTO> getAllUsersByRole(String role) {
        List<Users> users = userRepository.findAllByRole(Role.USER);
        return users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> searchUsersByFio(String query) {
        List<Users> users = userRepository.searchByFullName(query);
        return users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDTO getUserDetails(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        UserDetailsDTO details = userMapper.toUserDetailsDTO(user);

        int ordersCount = orderRepository.countByUsersId(userId);
        details.setOrdersCount(ordersCount);
        return details;
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<?> orders = orderRepository.findAllByUsersId(userId);
        return orders.stream()
                .map(o -> orderMapper.toOrderDTO((Orders) o))
                .collect(Collectors.toList());
    }

}