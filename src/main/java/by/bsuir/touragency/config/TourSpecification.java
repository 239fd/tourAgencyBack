package by.bsuir.touragency.config;

import by.bsuir.touragency.dto.TourSearchRequest;
import by.bsuir.touragency.entity.Tours;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TourSpecification {
    public static Specification<Tours> withFilters(TourSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String kw = "%" + request.getKeyword().toLowerCase() + "%";
                Predicate name = cb.like(cb.lower(root.get("name")), kw);
                Predicate country = cb.like(cb.lower(root.get("country")), kw);
                predicates.add(cb.or(name, country));
            }

            if (request.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }
            if (request.getDays() != null) {
                predicates.add(cb.equal(root.get("numberOfDays"), request.getDays()));
            }
            if (request.getCountry() != null && !request.getCountry().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("country")), "%" + request.getCountry().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}