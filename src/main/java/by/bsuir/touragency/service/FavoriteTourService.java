package by.bsuir.touragency.service;


import by.bsuir.touragency.dto.TourDTO;

import java.util.List;

public interface FavoriteTourService {
    void addFavoriteTourByEmail(String email    , Long tourId);
    List<TourDTO> getFavoriteToursByUser(String email);
    void removeFavoriteTourByEmail(String email, Long tourId);
}
