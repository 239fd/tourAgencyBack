package by.bsuir.touragency.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

public class OrderHasLanguageId implements Serializable {
    private Long orderId;
    private Integer languageId;
}