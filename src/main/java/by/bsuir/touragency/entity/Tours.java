package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tours")
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "country")
    private String country;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "price")
    private Double price;

    @Column(name = "program")
    private String program;

    @ManyToMany(mappedBy = "tours")
    private Set<Languages> languages;

    @OneToMany(mappedBy = "tour")
    private Set<UsersFavoriteTour> usersFavoriteTours;

    @OneToMany(mappedBy = "tour")
    private Set<TourPhoto> tourPhotos;

}
