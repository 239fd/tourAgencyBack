package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {

    @Id
    public long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "name_of_tour")
    private String nameOfTour;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "special_request")
    private String specialRequest;

    @Column(name = "status")
    private String status;

    @Column(name = "update_status_date")
    private String updateStatusDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "tour_id")
    private Long tourId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @ManyToMany
    @JoinTable(name = "orders_has_languages",
            joinColumns = @JoinColumn(name = "orders_id"))
    private Set<Languages> languages = new LinkedHashSet<>();

}
