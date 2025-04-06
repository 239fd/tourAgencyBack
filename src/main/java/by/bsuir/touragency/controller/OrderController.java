package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.*;
import by.bsuir.touragency.service.OrderService;
import by.bsuir.touragency.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderDTO> orders = orderService.getOrdersByUser(email);

        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .data(orders)
                .status(true)
                .message("User orders retrieved successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/form-data")
    public ResponseEntity<ApiResponse<OrderFormDataDTO>> getOrderFormData(
            @RequestParam Long tourId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderFormDataDTO formData = orderService.getOrderFormData(email, tourId, startDate);

        ApiResponse<OrderFormDataDTO> response = ApiResponse.<OrderFormDataDTO>builder()
                .data(formData)
                .status(true)
                .message("Order form data retrieved successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<OrderDTO>> submitOrder(@RequestBody OrderSubmissionDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderDTO order = orderService.submitOrder(email, request);

        ApiResponse<OrderDTO> response = ApiResponse.<OrderDTO>builder()
                .data(order)
                .status(true)
                .message("Order submitted successfully")
                .build();

        return ResponseEntity.ok(response);
    }


}
