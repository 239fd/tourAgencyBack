package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.dto.ToursDTO;
import by.bsuir.touragency.extentions.TourNotFoundException;
import by.bsuir.touragency.service.FavoriteTourService;
import by.bsuir.touragency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tours")
public class ToursController {

    private final TourService tourService;
    private final FavoriteTourService favoriteTourService;

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

    @GetMapping("/")
    public ResponseEntity<ApiResponse<OneTourDTO>> getAllTours(@RequestParam long id) {
        try {
            OneTourDTO tourDTO = tourService.getTourById(id);
            ApiResponse<OneTourDTO> response = ApiResponse.<OneTourDTO>builder()
                    .data(tourDTO)
                    .status(true)
                    .message("All Tours returned")
                    .build();

            return ResponseEntity.ok(response);
        }
        catch(TourNotFoundException e){
            ApiResponse<OneTourDTO> response = ApiResponse.<OneTourDTO>builder()
                    .data(null)
                    .status(false)
                    .message("No tours with id = " + id)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

    }

    @PostMapping("/favorites")
    public ResponseEntity<ApiResponse<String>> addTourToFavorites(@RequestParam Long favoriteTourId) {
        favoriteTourService.addFavoriteTour(1L, favoriteTourId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .data("Tour added to favorites")
                .status(true)
                .message("Tour successfully added to favorites")
                .build();
        return ResponseEntity.ok(response);
    }

}