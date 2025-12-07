package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import pl.wsb.fitnesstracker.healthmetrics.HealthMetrics;
import pl.wsb.fitnesstracker.statistics.api.Statistics;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA entity representing an application user.
 *
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Statistics statistics;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<HealthMetrics> healthMetrics;

    /**
     * Construct a new User instance.
     *
     * @param firstName first name, must not be null
     * @param lastName  last name, must not be null
     * @param birthdate birthdate, must not be null
     * @param email     unique e-mail address, must not be null
     */
    public User(String firstName, String lastName, LocalDate birthdate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    /**
     * Replaces the core data of this user with values from another User object.
     */
    public void replace(User other) {
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.birthdate = other.birthdate;
        this.email = other.email;
    }
}
