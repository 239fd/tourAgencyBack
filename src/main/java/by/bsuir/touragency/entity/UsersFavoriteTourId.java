package by.bsuir.touragency.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Embeddable
public class UsersFavoriteTourId implements java.io.Serializable {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tour_id", nullable = false)
    private Long tourId;

}