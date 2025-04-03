package by.bsuir.touragency.service;

import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;

import java.util.List;

public interface TourService {
     List<TourDTO> getAllTours();

     OneTourDTO getTourById(long id);
}
