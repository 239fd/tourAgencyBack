package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.dto.TourSearchRequest;

import java.util.List;

public interface TourService {
     List<TourDTO> getAllTours();
     OneTourDTO getTourById(long id);

     OneTourDTO getTourByIdWithDates(long id);

     List<TourDTO> searchAndFilter(TourSearchRequest request);
}
