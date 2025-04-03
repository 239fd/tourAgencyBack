package by.bsuir.touragency.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
public class UsersFavoriteTourId implements java.io.Serializable {
    private static final long serialVersionUID = 7808900629452267272L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tour_id", nullable = false)
    private Long tourId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsersFavoriteTourId entity = (UsersFavoriteTourId) o;
        return Objects.equals(this.tourId, entity.tourId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourId, userId);
    }

}