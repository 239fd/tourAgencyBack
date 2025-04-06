package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.dto.TourEditingDTO;
import by.bsuir.touragency.dto.TourSearchRequest;

import java.util.List;

public interface TourService {
     List<TourDTO> getAllTours();
     OneTourDTO getTourByIdWithDates(long id);
     List<TourDTO> searchAndFilter(TourSearchRequest request);
     TourDTO createTour(TourDTO dto);
     TourDTO updateTour(Long id, TourEditingDTO dto);
     void deleteTour(Long id);
     TourDTO updateTourPhoto(Long tourId, Long photoId, String newPhotoBase64);
     TourDTO getTourDetailsForAdmin(Long id);
}
