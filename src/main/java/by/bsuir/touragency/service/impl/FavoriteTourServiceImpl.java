package by.bsuir.touragency.service.impl;


import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.entity.Users;
import by.bsuir.touragency.entity.UsersFavoriteTour;
import by.bsuir.touragency.entity.UsersFavoriteTourId;
import by.bsuir.touragency.mappers.TourMapper;
import by.bsuir.touragency.repository.FavoriteTourRepository;
import by.bsuir.touragency.repository.TourRepository;
import by.bsuir.touragency.repository.UserRepository;
import by.bsuir.touragency.service.FavoriteTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteTourServiceImpl implements FavoriteTourService {

    private final UserRepository userRepository;
    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final FavoriteTourRepository favoriteTourRepository;

    @Override
    public void addFavoriteTourByEmail(String email, Long tourId) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        tourRepository.findById(tourId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found"));


        UsersFavoriteTourId id = new UsersFavoriteTourId(user.getId(), tourId);

        if (!favoriteTourRepository.existsById(id)) {
            UsersFavoriteTour favoriteTour = new UsersFavoriteTour();
            favoriteTour.setUserId(user.getId());
            favoriteTour.setTourId(tourId);
            favoriteTour.setCreatedDate(Instant.now());
            favoriteTourRepository.save(favoriteTour);
        }
    }

    @Override
    public void removeFavoriteTourByEmail(String email, Long tourId) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found"));

        tourRepository.findById(tourId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour with ID " + tourId + " not found"));

        UsersFavoriteTourId id = new UsersFavoriteTourId(user.getId(), tourId);

        if (!favoriteTourRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour is not in user's favorites");
        }

        favoriteTourRepository.deleteById(id);
    }

    @Override
    public List<TourDTO> getFavoriteToursByUser(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return user.getUsersFavoriteTours().stream()
                .map(uf -> tourMapper.toTourDTO(uf.getTour()))
                .collect(Collectors.toList());
    }
}
