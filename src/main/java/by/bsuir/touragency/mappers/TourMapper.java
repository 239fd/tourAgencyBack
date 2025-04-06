package by.bsuir.touragency.mappers;

import by.bsuir.touragency.dto.OneTourDTO;
import by.bsuir.touragency.dto.TourDTO;
import by.bsuir.touragency.entity.Languages;
import by.bsuir.touragency.entity.Tours;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TourMapper {

    @Named("toDto")
    @Mapping(target = "languages", expression = "java(tour.getLanguages() == null ? null : tour.getLanguages().stream().map(lang -> lang.getLanguage()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "mapInstantToLocalDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "mapInstantToLocalDate")
    TourDTO toDto(Tours tour);

    @Mapping(target = "languages", expression = "java(tour.getLanguages() == null ? null : tour.getLanguages().stream().map(lang -> lang.getLanguage()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "mapInstantToLocalDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "mapInstantToLocalDate")
    TourDTO toTourDTO(Tours tour);

    @Mapping(target = "languages", expression = "java(tour.getLanguages() == null ? null : tour.getLanguages().stream().map(lang -> lang.getLanguage()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "mapInstantToLocalDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "mapInstantToLocalDate")
    OneTourDTO toOneTourDTO(Tours tour);

    @IterableMapping(qualifiedByName = "toDto")
    List<TourDTO> toDtoList(List<Tours> tours);

    @Mapping(target = "languages", source = "languages")
    Tours toEntity(TourDTO tourDTO);

    default Set<Languages> mapLanguages(List<String> languageNames) {
        if (languageNames == null) {
            return null;
        }
        return languageNames.stream().map(name -> {
            Languages language = new Languages();
            language.setLanguage(name);
            return language;
        }).collect(java.util.stream.Collectors.toSet());
    }

    @Named("mapInstantToLocalDate")
    default LocalDate mapInstantToLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    default Instant mapLocalDateToInstant(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
