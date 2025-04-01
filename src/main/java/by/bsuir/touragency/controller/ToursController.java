package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.dto.ToursDTO;
import by.bsuir.touragency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tours")
public class ToursController {

    private final TourService tourService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<ToursDTO>> getAllTours() {
        List<TourDTO> toursDTOS = tourService.getAllTours();

        ToursDTO toursDTO = new ToursDTO();
        toursDTO.setAmount(toursDTOS.size());
        toursDTO.setTours(toursDTOS);

        ApiResponse<ToursDTO> response = ApiResponse.<ToursDTO>builder()
                .data(toursDTO)
                .status(true)
                .message("All Tours returned")
                .build();

        return ResponseEntity.ok(response);
    }
}
