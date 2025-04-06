package by.bsuir.touragency.service.impl;

import by.bsuir.touragency.config.TourSpecification;
import by.bsuir.touragency.dto.*;
import by.bsuir.touragency.entity.Languages;
import by.bsuir.touragency.entity.TourHasLanguage;
import by.bsuir.touragency.entity.TourPhoto;
import by.bsuir.touragency.entity.Tours;
import by.bsuir.touragency.exceptions.TourNotFoundException;
import by.bsuir.touragency.mappers.TourMapper;
import by.bsuir.touragency.repository.LanguageRepository;
import by.bsuir.touragency.repository.TourHasLanguageRepository;
import by.bsuir.touragency.repository.TourPhotoRepository;
import by.bsuir.touragency.repository.TourRepository;
import by.bsuir.touragency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final LanguageRepository languageRepository;
    private final TourPhotoRepository tourPhotoRepository;
    private final TourHasLanguageRepository tourHasLanguageRepository;

    @Override
    public List<TourDTO> getAllTours() {
        List<Tours> tourEntities = tourRepository.findAll();
        List<TourDTO> tourDTOs = tourMapper.toDtoList(tourEntities);

        for (TourDTO dto : tourDTOs) {
            List<String> photoBase64 = tourPhotoRepository.findByTourId(dto.getId())
                    .stream()
                    .map(photo -> Base64.getEncoder().encodeToString(photo.getPhotoData()))
                    .collect(Collectors.toList());
            dto.setPhotos(photoBase64);
        }
        return tourDTOs;
    }

    @Override
    public OneTourDTO getTourByIdWithDates(long id) {
        Tours tour = tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Тур не найден"));

        List<Tours> similar = tourRepository.findAllByName(tour.getName());
        List<LocalDate> dates = similar.stream()
                .map(t -> t.getStartDate() == null
                        ? null
                        : t.getStartDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .filter(date -> date != null)
                .sorted()
                .collect(Collectors.toList());


        OneTourDTO dto = tourMapper.toOneTourDTO(tour);
        dto.setStartDates(dates);

        List<String> photoBase64 = tourPhotoRepository.findByTourId(tour.getId())
                .stream()
                .map(photo -> Base64.getEncoder().encodeToString(photo.getPhotoData()))
                .collect(Collectors.toList());
        dto.setPhotos(photoBase64);

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
        List<TourDTO> tourDTOs = tourMapper.toDtoList(tours);
        for (TourDTO dto : tourDTOs) {
            List<String> photoBase64 = tourPhotoRepository.findByTourId(dto.getId())
                    .stream()
                    .map(photo -> Base64.getEncoder().encodeToString(photo.getPhotoData()))
                    .collect(Collectors.toList());
            dto.setPhotos(photoBase64);
        }
        return tourDTOs;
    }

    @Override
    public TourDTO createTour(TourDTO dto) {
        Tours tour = Tours.builder()
                .name(dto.getName())
                .country(dto.getCountry())
                .location(dto.getLocation())
                .numberOfDays(dto.getNumberOfDays())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .program(dto.getProgram())
                .build();
        if (dto.getStartDate() != null) {
            Instant start = dto.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            tour.setStartDate(start);
        }
        if (dto.getEndDate() != null) {
            Instant end = dto.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            tour.setEndDate(end);
        }

        Tours saved = tourRepository.save(tour);

        if (dto.getLanguages() != null && !dto.getLanguages().isEmpty()) {
            for (String langName : dto.getLanguages()) {

                Set<Languages> lang = languageRepository.findByLanguage(langName);
                Languages language = lang.iterator().next();

                TourHasLanguage thl = new TourHasLanguage();
                thl.setTourId(saved.getId());
                thl.setLanguageId(language.getId());

                thl.setTour(saved);
                thl.setLanguage(language);

                tourHasLanguageRepository.save(thl);
            }
        }

        if (dto.getPhotos() != null && !dto.getPhotos().isEmpty()) {
            for (String photoBase64 : dto.getPhotos()) {
                byte[] photoData = Base64.getDecoder().decode(photoBase64);
                TourPhoto photo = new TourPhoto();
                photo.setPhotoData(photoData);
                photo.setTour(saved);
                tourPhotoRepository.save(photo);
            }
        }
        return tourMapper.toDto(saved);
    }

    @Override
    public TourDTO updateTour(Long id, TourEditingDTO dto) {

        Tours existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Тур не найден"));

        existingTour.setName(dto.getName());
        existingTour.setCountry(dto.getCountry());
        existingTour.setLocation(dto.getLocation());
        existingTour.setNumberOfDays(dto.getNumberOfDays());
        existingTour.setPrice(dto.getPrice());
        existingTour.setDescription(dto.getDescription());
        existingTour.setProgram(dto.getProgram());

        if (dto.getStartDate() != null) {
            Instant startInstant = dto.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            existingTour.setStartDate(startInstant);
        }
        if (dto.getEndDate() != null) {
            Instant endInstant = dto.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            existingTour.setEndDate(endInstant);
        }

        if (dto.getLanguages() != null && !dto.getLanguages().isEmpty()) {

            Set<Languages> languages = new HashSet<>(languageRepository.findByLanguageIn(dto.getLanguages()));
            existingTour.setLanguages(languages);
        }

        Tours updatedTour = tourRepository.save(existingTour);

        if (dto.getPhotos() != null) {
            for (PhotoDTO photoDTO : dto.getPhotos()) {
                if (photoDTO.getId() != null) {

                    TourPhoto existingPhoto = tourPhotoRepository.findById(photoDTO.getId())
                            .orElse(null);
                    if (existingPhoto != null && photoDTO.getBase64() != null && !photoDTO.getBase64().isEmpty()) {
                        byte[] newPhotoData = Base64.getDecoder().decode(photoDTO.getBase64());
                        existingPhoto.setPhotoData(newPhotoData);
                        tourPhotoRepository.save(existingPhoto);
                    }
                } else {
                    if (photoDTO.getBase64() != null && !photoDTO.getBase64().isEmpty()) {
                        byte[] photoData = Base64.getDecoder().decode(photoDTO.getBase64());
                        TourPhoto newPhoto = new TourPhoto();
                        newPhoto.setPhotoData(photoData);
                        newPhoto.setTour(updatedTour);
                        tourPhotoRepository.save(newPhoto);
                    }
                }
            }
        }

        return tourMapper.toDto(updatedTour);
    }


    @Override
    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new TourNotFoundException("Тур не найден");
        }
        tourRepository.deleteById(id);
    }
    @Override
    public TourDTO updateTourPhoto(Long tourId, Long photoId, String newPhotoBase64) {

        Tours tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new TourNotFoundException("Tour not found"));

        TourPhoto photo = tourPhotoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        if (!photo.getTour().getId().equals(tourId)) {
            throw new RuntimeException("Photo does not belong to tour");
        }
        byte[] photoData = Base64.getDecoder().decode(newPhotoBase64);
        photo.setPhotoData(photoData);
        tourPhotoRepository.save(photo);
        return getTourDetailsForAdmin(tourId);
    }

    @Override
    public TourDTO getTourDetailsForAdmin(Long id) {
        Tours tour = tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Tour not found"));
        TourDTO dto = tourMapper.toDto(tour);
        List<String> photoBase64 = tourPhotoRepository.findByTourId(tour.getId())
                .stream()
                .map(photo -> Base64.getEncoder().encodeToString(photo.getPhotoData()))
                .collect(java.util.stream.Collectors.toList());
        dto.setPhotos(photoBase64);
        return dto;
    }
}
