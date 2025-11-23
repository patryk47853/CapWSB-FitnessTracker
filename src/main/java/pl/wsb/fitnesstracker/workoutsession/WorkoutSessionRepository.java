package pl.wsb.fitnesstracker.workoutsession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing WorkoutSession using EntityManager.
 * Provides operations for saving and querying workout session data.
 */
@Repository
public class WorkoutSessionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Saves a new workout session into the database.
     * Data is without coordinates
     *
     * @param session WorkoutSession to be persisted
     *
     */
    @Transactional
    public void addWorkoutSession(WorkoutSession session) {
        String query = """
        INSERT INTO workout_session
            (training_id, timestamp, start_latitude, start_longitude, end_latitude, end_longitude, altitude)
        VALUES
            (:trainingId, :timestamp, :startLatitude, :startLongitude, :endLatitude, :endLongitude, :altitude)
        """;

        entityManager.createNativeQuery(query)
                .setParameter("trainingId", session.getTraining().getId())
                .setParameter("timestamp", session.getTimestamp())
                .setParameter("startLatitude", session.getStartLatitude())
                .setParameter("startLongitude", session.getStartLongitude())
                .setParameter("endLatitude", session.getEndLatitude())
                .setParameter("endLongitude", session.getEndLongitude())
                .setParameter("altitude", session.getAltitude())
                .executeUpdate();
    }
}
