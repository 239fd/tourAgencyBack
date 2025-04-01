package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="languages")
public class Languages {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "language")
    private String language;

    @ManyToMany(mappedBy = "languages")
    private Set<Orders> orders = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "tours_has_languages",
            joinColumns = @JoinColumn(name = "languages_id"),
            inverseJoinColumns = @JoinColumn(name = "tours_id"))
    private Set<Tours> tours = new LinkedHashSet<>();

}
