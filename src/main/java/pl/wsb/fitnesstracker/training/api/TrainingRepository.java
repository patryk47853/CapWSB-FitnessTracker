package pl.wsb.fitnesstracker.training.api;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Finds trainings for a user within a given time range.
     *
     * @param user user owner of trainings
     * @param from start date
     * @param to   end date
     * @return list of trainings
     */
    List<Training> findByUserAndStartTimeBetween(User user, Date from, Date to);
}
