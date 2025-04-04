package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@IdClass(UsersFavoriteTourId.class)
@Table(name = "users_favorite_tours")
public class UsersFavoriteTour {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "tour_id")
    private Long tourId;

    @ManyToOne
    @JoinColumn(name = "tour_id", insertable = false, updatable = false)
    private Tours tour;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    private Instant createdDate;

}