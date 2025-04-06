package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@IdClass(TourHasLanguageId.class)
@Table(name = "tours_has_languages")
public class TourHasLanguage {

    @Id
    @Column(name = "tours_id")
    private Long tourId;

    @Id
    @Column(name = "languages_id")
    private Integer languageId;

    @ManyToOne
    @JoinColumn(name = "tours_id", insertable = false, updatable = false)
    private Tours tour;

    @ManyToOne
    @JoinColumn(name = "languages_id", insertable = false, updatable = false)
    private Languages language;
}
