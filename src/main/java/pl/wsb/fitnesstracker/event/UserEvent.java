package pl.wsb.fitnesstracker.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.fitnesstracker.user.api.User;

/**
 * Represents the association between a User and an Event.

 * This entity models the many-to-many relationship between users and events,
 * allowing to track which users are participating in which events and their participation status.
 *
 */
@Entity
@Table(name = "user_event")
@Getter
@Setter
@NoArgsConstructor
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The participant of the event.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The event in which the user is participating.
     */
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * The participation status of the user in the event.
     * For example: "CONFIRMED", "PENDING", "CANCELLED".
     */
    @Column(nullable = false)
    private String status;
}
