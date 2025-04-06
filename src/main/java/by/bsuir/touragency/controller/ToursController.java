package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.*;
import by.bsuir.touragency.exceptions.TourNotFoundException;
import by.bsuir.touragency.service.FavoriteTourService;
import by.bsuir.touragency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ApiResponse<OneTourDTO>> getTour(@RequestParam long id) {
        OneTourDTO tourDTO = tourService.getTourByIdWithDates(id);
        ApiResponse<OneTourDTO> response = ApiResponse.<OneTourDTO>builder()
                .data(tourDTO)
                .status(true)
                .message("Tour retrieved successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/favorites")
    public ResponseEntity<ApiResponse<String>> addTourToFavorites(@RequestParam Long favoriteTourId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        favoriteTourService.addFavoriteTourByEmail(userEmail, favoriteTourId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data("Tour added to favorites")
                .status(true)
                .message("Tour successfully added to favorites")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<ApiResponse<String>> removeTourFromFavorites(@RequestParam Long tourId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        favoriteTourService.removeFavoriteTourByEmail(userEmail, tourId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data("Tour removed from favorites")
                .status(true)
                .message("Tour successfully removed from favorites")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favorites")
    public ResponseEntity<ApiResponse<List<TourDTO>>> getMyFavoriteTours() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        List<TourDTO> favoriteTours = favoriteTourService.getFavoriteToursByUser(email);

        ApiResponse<List<TourDTO>> response = ApiResponse.<List<TourDTO>>builder()
                .data(favoriteTours)
                .status(true)
                .message("Favorite tours retrieved successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<TourDTO>>> searchTours(@RequestBody TourSearchRequest request) {
        List<TourDTO> result = tourService.searchAndFilter(request);

        if (result.isEmpty()) {
            throw new TourNotFoundException("Ничего не найдено");
        }

        ApiResponse<List<TourDTO>> response = ApiResponse.<List<TourDTO>>builder()
                .data(result)
                .status(true)
                .message("Tours found successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TourDTO>> createTour(@RequestBody TourDTO tourDTO) {
        TourDTO created = tourService.createTour(tourDTO);
        ApiResponse<TourDTO> response = ApiResponse.<TourDTO>builder()
                .data(created)
                .status(true)
                .message("Tour created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TourDTO>> updateTour(@PathVariable Long id, @RequestBody TourEditingDTO tourDTO) {
        TourDTO updated = tourService.updateTour(id, tourDTO);
        ApiResponse<TourDTO> response = ApiResponse.<TourDTO>builder()
                .data(updated)
                .status(true)
                .message("Tour updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(true)
                .message("Tour deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{tourId}/photos/{photoId}")
    public ResponseEntity<ApiResponse<TourDTO>> updateTourPhoto(@PathVariable Long tourId,
                                                                @PathVariable Long photoId,
                                                                @RequestBody String newPhotoBase64) {
        TourDTO updatedTour = tourService.updateTourPhoto(tourId, photoId, newPhotoBase64);
        ApiResponse<TourDTO> response = ApiResponse.<TourDTO>builder()
                .data(updatedTour)
                .status(true)
                .message("Tour photo updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}

