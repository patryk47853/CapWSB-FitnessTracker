package pl.wsb.fitnesstracker.workoutsession;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.api.Training;

/**
 * Represents a workout session performed by a user.

 * Contains information about the associated training, session timestamp,
 * starting and ending location coordinates, and altitude.
 *
 */
@Entity
@Table(name = "workout_session")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private String timestamp;

    private double startLatitude;
    private double startLongitude;

    private double endLatitude;
    private double endLongitude;

    private double altitude;
}
