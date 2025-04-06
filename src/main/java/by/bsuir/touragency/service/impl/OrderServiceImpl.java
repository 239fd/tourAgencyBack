package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.*;
import by.bsuir.touragency.entity.*;
import by.bsuir.touragency.exceptions.UserException;
import by.bsuir.touragency.exceptions.TourNotFoundException;
import by.bsuir.touragency.mappers.OrderMapper;
import by.bsuir.touragency.repository.*;
import by.bsuir.touragency.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TourRepository tourRepository;
    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final OrderHasLanguageRepository orderHasLanguageRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    @Override
    public List<OrderDTO> getOrdersByUser(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Пользователь не найден"));

        List<Orders> orders = orderRepository.findAllByUsers(user);

        return orderMapper.toDtoList(orders);
    }

    @Override
    public OrderFormDataDTO getOrderFormData(String email, Long tourId, LocalDate startDate) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Пользователь не найден"));

        Tours tour = tourRepository.findByIdAndStartDate(tourId, startDate)
                .orElseThrow(() -> new TourNotFoundException("Тур не найден"));

        List<LanguageDTO> languageDtos = tour.getLanguages().stream()
                .map(lang -> new LanguageDTO(lang.getId(), lang.getLanguage()))
                .toList();


        return OrderFormDataDTO.builder()
                .tourName(tour.getName())
                .country(tour.getCountry())
                .location(tour.getLocation())
                .description(tour.getDescription())
                .numberOfDays(tour.getNumberOfDays())
                .price(tour.getPrice())
                .startDate(tour.getStartDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .endDate(tour.getEndDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .languages(languageDtos)
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .phoneNumber(user.getPhoneNumber())
                .passportSeries(user.getPassportSeries())
                .passportNumber(user.getPassportNumber())
                .build();
    }

    @Override
    public OrderDTO submitOrder(String email, OrderSubmissionDTO request) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("Пользователь не найден"));

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPatronymic(request.getPatronymic());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassportSeries(request.getPassportSeries());
        user.setPassportNumber(request.getPassportNumber());

        Tours tour = tourRepository.findByIdAndStartDate(request.getTourId(), request.getStartDate())
                .orElseThrow(() -> new TourNotFoundException("Тур не найден"));

        double totalPrice = tour.getPrice();
        if (user.getBalance() == null || user.getBalance() < totalPrice) {
            throw new UserException("Недостаточно средств на балансе для оформления заявки.");
        }
        user.setBalance(user.getBalance() - totalPrice);
        userRepository.save(user);

        Orders order = orderMapper.fromSubmissionDTO(request);
        order.setUsers(user);
        order.setTourId(tour.getId());
        order.setNameOfTour(tour.getName());
        order.setNumberOfDays(tour.getNumberOfDays());
        order.setNumberOfPeople(request.getNumberOfPeople());
        order.setEndDate(tour.getEndDate().atZone(ZoneId.systemDefault()).toLocalDate());
        order.setDate(tour.getStartDate().atZone(ZoneId.systemDefault()).toLocalDate());
        order.setCreatedDate(Instant.now());
        order.setUpdateStatusDate(String.valueOf(Instant.now()));
        order.setStatus("В рассмотрении");
        order.setSpecialRequest(request.getSpecialRequest());

        Set<Languages> languages = languageRepository.findByLanguage(request.getLanguage());
        order.setLanguages(languages);

        Orders saved = orderRepository.save(order);

        OrderHasLanguage orderHasLanguage = new OrderHasLanguage();
        orderHasLanguage.setOrderId(saved.getId());
        orderHasLanguage.setLanguageId(languages.iterator().next().getId());
        orderHasLanguageRepository.save(orderHasLanguage);

        return orderMapper.toOrderDTO(saved);
    }


}
