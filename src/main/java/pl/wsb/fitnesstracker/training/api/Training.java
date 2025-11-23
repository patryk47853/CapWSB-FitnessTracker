package pl.wsb.fitnesstracker.training.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

/**
 * Represents a training session performed by a user.

 * Contains information about the user who performed the training,
 * start and end times, type of activity, distance covered, and average speed.
 *
 */
@Entity
@Table(name = "trainings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who performed the training session.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The start date and time of the training session.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startTime;

    /**
     * The end date and time of the training session.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endTime;

    /**
     * Type of physical activity performed.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    /**
     * Distance covered during the training session (in kilometers).
     */
    @Column(nullable = false)
    private double distance;

    /**
     * Average speed during the training session (in km/h).
     */
    @Column(nullable = false)
    private double averageSpeed;

    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}
