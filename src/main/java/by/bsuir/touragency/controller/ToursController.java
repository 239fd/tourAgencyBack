package by.bsuir.touragency.controller;

import by.bsuir.touragency.API.ApiResponse;
import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.dto.ToursDTO;
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
}

/*TODO
1) Попытка добавить несуществующий тур
2) Пустые любимые туры
 */