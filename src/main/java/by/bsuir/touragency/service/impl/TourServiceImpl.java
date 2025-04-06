package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.config.TourSpecification;
import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.dto.TourSearchRequest;
import by.bsuir.touragency.entity.Tours;
import by.bsuir.touragency.exceptions.TourNotFoundException;
import by.bsuir.touragency.mappers.TourMapper;
import by.bsuir.touragency.repository.TourRepository;
import by.bsuir.touragency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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

    @Override
    public OneTourDTO getTourByIdWithDates(long id){
        Tours tour = tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Тур не найден"));

        List<Tours> similar = tourRepository.findAllByName(tour.getName());
        List<LocalDate> dates = similar.stream()
                .map(t -> t.getStartDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .sorted()
                .toList();

        OneTourDTO dto = tourMapper.toOneTourDTO(tour);
        dto.setStartDates(dates);
        return dto;
    }

    @Override
    public List<TourDTO> searchAndFilter(TourSearchRequest request) {
        Specification<Tours> spec = TourSpecification.withFilters(request);
        String sortBy = Optional.ofNullable(request.getSortBy()).orElse("default");
        Sort sort = switch (sortBy) {
            case "price_asc" -> Sort.by("price").ascending();
            case "price_desc" -> Sort.by("price").descending();
            default -> Sort.by("id").descending();
        };

        List<Tours> tours = tourRepository.findAll(spec, sort);
        return tourMapper.toDtoList(tours);
    }
}
