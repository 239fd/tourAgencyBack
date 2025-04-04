package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="tours")
public class Tours {

    @Id
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
    private Set<Languages> languages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tour")
    private Set<UsersFavoriteTour> usersFavoriteTours = new LinkedHashSet<>();

}
