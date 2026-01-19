package pl.wsb.fitnesstracker.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Service responsible for generating weekly training reports
 * for all users in the system.
 *
 * The report includes aggregated data and detailed training entries.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class WeeklyTrainingReportService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final WeeklyTrainingEmailReportSender emailReportSender;

    /**
     * Generates weekly reports for all users.
     * Reports cover the previous calendar week (Monday–Sunday).
     */
    void generateWeeklyReports() {
        LocalDate startOfWeek = LocalDate.now()
                .minusWeeks(1)
                .with(DayOfWeek.MONDAY);

        LocalDate endOfWeek = startOfWeek.plusDays(6);

        Date from = Date.from(startOfWeek
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        Date to = Date.from(endOfWeek
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        for (User user : userRepository.findAll()) {
            List<Training> trainings =
                    trainingRepository.findByUserAndStartTimeBetween(user, from, to);

            logWeeklyReport(user, trainings, startOfWeek, endOfWeek);

            emailReportSender.sendReport(user, trainings, startOfWeek, endOfWeek);
        }
    }

    private void logWeeklyReport(User user,
                                 List<Training> trainings,
                                 LocalDate startOfWeek,
                                 LocalDate endOfWeek) {

        int trainingsCount = trainings.size();
        double totalDistance = calculateTotalDistance(trainings);
        long totalDurationMinutes = calculateTotalDurationMinutes(trainings);

        log.info("=================================");
        log.info("WEEKLY TRAINING REPORT");
        log.info("User: {} {}", user.getFirstName(), user.getLastName());
        log.info("Email: {}", user.getEmail());
        log.info("Period: {} - {}", startOfWeek, endOfWeek);
        log.info("Trainings count: {}", trainingsCount);
        log.info("Total distance: {} km", totalDistance);
        log.info("Total duration: {} minutes", totalDurationMinutes);

        if (trainings.isEmpty()) {
            log.info("No trainings registered for this period.");
            return;
        }

        log.info("---- TRAINING DETAILS ----");
        trainings.forEach(training ->
                log.info(
                        "Date: {}, Activity: {}, Distance: {} km, Avg speed: {} km/h",
                        training.getStartTime(),
                        training.getActivityType(),
                        training.getDistance(),
                        training.getAverageSpeed()
                )
        );
    }

    private double calculateTotalDistance(List<Training> trainings) {
        return trainings.stream()
                .mapToDouble(Training::getDistance)
                .sum();
    }

    private long calculateTotalDurationMinutes(List<Training> trainings) {
        return trainings.stream()
                .mapToLong(training ->
                        (training.getEndTime().getTime()
                                - training.getStartTime().getTime()) / 60_000
                )
                .sum();
    }
}
