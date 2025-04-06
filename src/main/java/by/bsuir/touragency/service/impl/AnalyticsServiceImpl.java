package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.AnalyticsDTO;
import by.bsuir.touragency.entity.Enum.Role;
import by.bsuir.touragency.entity.Orders;
import by.bsuir.touragency.entity.Users;
import by.bsuir.touragency.repository.OrderRepository;
import by.bsuir.touragency.repository.UserRepository;
import by.bsuir.touragency.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public AnalyticsDTO getOverallAnalytics() {
        List<Orders> orders = orderRepository.findAll();
        long totalOrders = orders.size();
        
        Map<String, Long> ordersStatusCount = orders.stream()
                .filter(o -> o.getStatus() != null)
                .collect(Collectors.groupingBy(Orders::getStatus, Collectors.counting()));
        
        List<Users> users = userRepository.findAllByRole(Role.USER);
        long totalClients = users.size();

        List<Integer> ages = users.stream()
                .filter(u -> u.getAge() != null)
                .map(Users::getAge)
                .collect(Collectors.toList());
        double overallAverageAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        List<Integer> maleAges = users.stream()
                .filter(u -> u.getAge() != null && "Мужской".equalsIgnoreCase(u.getGender()))
                .map(Users::getAge)
                .collect(Collectors.toList());
        double averageAgeMale = maleAges.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        List<Integer> femaleAges = users.stream()
                .filter(u -> u.getAge() != null && "Женский".equalsIgnoreCase(u.getGender()))
                .map(Users::getAge)
                .collect(Collectors.toList());
        double averageAgeFemale = femaleAges.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        long maleCount = users.stream().filter(u -> "Мужской".equalsIgnoreCase(u.getGender())).count();
        long femaleCount = users.stream().filter(u -> "Женский".equalsIgnoreCase(u.getGender())).count();
        long totalWithGender = maleCount + femaleCount;
        double percentageMale = totalWithGender > 0 ? (maleCount * 100.0 / totalWithGender) : 0;
        double percentageFemale = totalWithGender > 0 ? (femaleCount * 100.0 / totalWithGender) : 0;
        
        return AnalyticsDTO.builder()
                .totalOrders(totalOrders)
                .ordersStatusCount(ordersStatusCount)
                .totalClients(totalClients)
                .overallAverageAge(overallAverageAge)
                .averageAgeMale(averageAgeMale)
                .averageAgeFemale(averageAgeFemale)
                .percentageMale(percentageMale)
                .percentageFemale(percentageFemale)
                .build();
    }

    @Override
    public AnalyticsDTO getAnalyticsForPeriod(Instant start, Instant end) {

        List<Orders> orders = orderRepository.findAllByCreatedDateBetween(start, end);
        long totalOrders = orders.size();
        
        Map<String, Long> ordersStatusCount = orders.stream()
                .filter(o -> o.getStatus() != null)
                .collect(Collectors.groupingBy(Orders::getStatus, Collectors.counting()));
        
        List<Users> users = userRepository.findAllByRole(Role.USER);
        long totalClients = users.size();

        List<Integer> ages = users.stream()
                .filter(u -> u.getAge() != null)
                .map(Users::getAge)
                .collect(Collectors.toList());
        double overallAverageAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        List<Integer> maleAges = users.stream()
                .filter(u -> u.getAge() != null && "Мужской".equalsIgnoreCase(u.getGender()))
                .map(Users::getAge)
                .collect(Collectors.toList());
        double averageAgeMale = maleAges.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        List<Integer> femaleAges = users.stream()
                .filter(u -> u.getAge() != null && "Женский".equalsIgnoreCase(u.getGender()))
                .map(Users::getAge)
                .collect(Collectors.toList());
        double averageAgeFemale = femaleAges.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        
        long maleCount = users.stream().filter(u -> "Мужской".equalsIgnoreCase(u.getGender())).count();
        long femaleCount = users.stream().filter(u -> "Женский".equalsIgnoreCase(u.getGender())).count();
        long totalWithGender = maleCount + femaleCount;
        double percentageMale = totalWithGender > 0 ? (maleCount * 100.0 / totalWithGender) : 0;
        double percentageFemale = totalWithGender > 0 ? (femaleCount * 100.0 / totalWithGender) : 0;
        
        return AnalyticsDTO.builder()
                .totalOrders(totalOrders)
                .ordersStatusCount(ordersStatusCount)
                .totalClients(totalClients)
                .overallAverageAge(overallAverageAge)
                .averageAgeMale(averageAgeMale)
                .averageAgeFemale(averageAgeFemale)
                .percentageMale(percentageMale)
                .percentageFemale(percentageFemale)
                .build();
    }
}
