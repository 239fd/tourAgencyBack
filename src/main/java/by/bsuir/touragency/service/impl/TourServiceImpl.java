package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.entity.Tours;
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
}
