package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.*;
import by.bsuir.touragency.entity.Enum.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserProfileDTO getProfile(String email);
    void updateProfile(String email, UserProfileDTO dto);
    BalanceDTO topUpBalance(String email, Double amount);
    List<UserDTO> getAllUsersByRole(String role);
    List<UserDTO> searchUsersByFio(String query);
    UserDetailsDTO getUserDetails(Long userId);
    List<OrderDTO> getUserOrders(Long userId);
}
