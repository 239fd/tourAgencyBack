package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.BalanceDTO;
import by.bsuir.touragency.dto.UserProfileDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserProfileDTO getProfile(String email);
    void updateProfile(String email, UserProfileDTO dto);
    BalanceDTO topUpBalance(String email, Double amount);
}
