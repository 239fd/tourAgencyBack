package by.bsuir.touragency.service.impl;


import by.bsuir.touragency.repository.FavoriteTourRepository;
import by.bsuir.touragency.service.FavoriteTourService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteTourServiceImpl implements FavoriteTourService {

    private final FavoriteTourRepository favoriteTourRepository;

    public FavoriteTourServiceImpl(FavoriteTourRepository favoriteTourRepository) {
        this.favoriteTourRepository = favoriteTourRepository;
    }

    @Override
    @Transactional
    public void addFavoriteTour(Long userId, Long tourId) {
//        UsersFavoriteTourId id = new UsersFavoriteTourId(userId, tourId);
//        if (!favoriteTourRepository.existsById(id)) {
//            UsersFavoriteTour favoriteTour = new UsersFavoriteTour(userId, tourId);
//            favoriteTourRepository.save(favoriteTour);
//        }
    }
}
