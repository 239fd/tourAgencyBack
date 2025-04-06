package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.OrderDTO;
import by.bsuir.touragency.dto.UserDTO;
import by.bsuir.touragency.dto.UserDetailsDTO;
import by.bsuir.touragency.service.OrderService;
import by.bsuir.touragency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsersByRole("USER");
        ApiResponse<List<UserDTO>> response = ApiResponse.<List<UserDTO>>builder()
                .data(users)
                .status(true)
                .message("Users retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserDTO>>> searchUsers(@RequestParam("query") String query) {
        List<UserDTO> users = userService.searchUsersByFio(query);
        ApiResponse<List<UserDTO>> response = ApiResponse.<List<UserDTO>>builder()
                .data(users)
                .status(true)
                .message("Search completed")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetailsDTO>> getUserDetails(@PathVariable Long userId) {
        UserDetailsDTO details = userService.getUserDetails(userId);
        ApiResponse<UserDetailsDTO> response = ApiResponse.<UserDetailsDTO>builder()
                .data(details)
                .status(true)
                .message("User details retrieved")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getUserOrders(@PathVariable Long userId) {
        List<OrderDTO> orders = userService.getUserOrders(userId);
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .data(orders)
                .status(true)
                .message("User orders retrieved")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .data(orders)
                .status(true)
                .message("All orders retrieved")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/orders/search")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> searchOrders(@RequestParam("query") String query) {
        List<OrderDTO> orders = orderService.searchOrders(query);
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .data(orders)
                .status(true)
                .message("Orders search completed")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(@PathVariable Long orderId,
                                                                   @RequestParam("status") String newStatus) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        ApiResponse<OrderDTO> response = ApiResponse.<OrderDTO>builder()
                .data(updatedOrder)
                .status(true)
                .message("Order status updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }


}
