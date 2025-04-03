package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.entity.Tours;
import by.bsuir.touragency.extentions.TourNotFoundException;
import by.bsuir.touragency.mappers.TourMapper;
import by.bsuir.touragency.repository.TourRepository;
import by.bsuir.touragency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    @Override
    public List<TourDTO> getAllTours() {
        List<Tours> tourEntities = tourRepository.findAll();
        return tourMapper.toDtoList(tourEntities);
    }

    @Override
    public OneTourDTO getTourById(long id){
        Tours tourEntity = tourRepository.findById(id).orElse(null);
        if(tourEntity != null){
            return tourMapper.toOneTourDTO(tourEntity);
        }
        throw new TourNotFoundException("Tour not found with id = " + id);

    }

    /*
    TODO
    1) Проверка на индекс(хибер)
    2) Исключения к получению всех туров если их нет
     */
}
