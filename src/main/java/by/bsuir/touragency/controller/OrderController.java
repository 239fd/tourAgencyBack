package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.OrderDTO;
import by.bsuir.touragency.dto.ToursDTO;
import by.bsuir.touragency.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/")

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllTours() {

        List<OrderDTO> orders = orderService.getAllOrders();

        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .data(orders)
                .status(true)
                .message("All Tours returned")
                .build();

        return ResponseEntity.ok(response);
    }
}
