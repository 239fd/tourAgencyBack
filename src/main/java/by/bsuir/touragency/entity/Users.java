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
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id", nullable = false)
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

    @Lob
    @Column(name = "role")
    private String role;

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

}
