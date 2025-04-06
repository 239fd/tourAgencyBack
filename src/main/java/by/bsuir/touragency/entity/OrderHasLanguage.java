package by.bsuir.touragency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@IdClass(OrderHasLanguageId.class)
@Table(name = "orders_has_languages")
public class OrderHasLanguage {

    @Id
    @Column(name = "orders_id")
    private Long orderId;

    @Id
    @Column(name = "languages_id")
    private Integer languageId;

    @ManyToOne
    @JoinColumn(name = "orders_id", insertable = false, updatable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "languages_id", insertable = false, updatable = false)
    private Languages language;
}
