package by.bsuir.touragency.entity;

import by.bsuir.touragency.entity.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "surname")
    private String surname;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "birthday")
    private Instant birthday;

    @OneToMany(mappedBy = "users")
    private Set<Orders> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UsersFavoriteTour> usersFavoriteTours = new LinkedHashSet<>();

}
