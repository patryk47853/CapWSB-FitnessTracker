package pl.wsb.fitnesstracker.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Component responsible for sending weekly training reports via email.
 */
@Component
@RequiredArgsConstructor
class WeeklyTrainingEmailReportSender {

    private final EmailSender emailSender;

    /**
     * Sends a weekly training report email to the given user.
     *
     * @param user        report recipient
     * @param trainings   list of trainings from the reported week
     * @param startOfWeek start date of the report period
     * @param endOfWeek   end date of the report period
     */
    void sendReport(User user,
                    List<Training> trainings,
                    LocalDate startOfWeek,
                    LocalDate endOfWeek) {

        EmailDto email = new EmailDto(
                user.getEmail(),
                "Weekly training report (" + startOfWeek + " - " + endOfWeek + ")",
                buildEmailContent(user, trainings, startOfWeek, endOfWeek)
        );

        emailSender.send(email);
    }

    private String buildEmailContent(User user,
                                     List<Training> trainings,
                                     LocalDate startOfWeek,
                                     LocalDate endOfWeek) {

        double totalDistance = trainings.stream()
                .mapToDouble(Training::getDistance)
                .sum();

        long totalDurationMinutes = trainings.stream()
                .mapToLong(training ->
                        (training.getEndTime().getTime()
                                - training.getStartTime().getTime()) / 60_000
                )
                .sum();

        String trainingDetails = trainings.isEmpty()
                ? "No trainings registered this week."
                : trainings.stream()
                .map(training -> """
                            - %s | %s | %.2f km | %.2f km/h
                            """.formatted(
                        training.getStartTime(),
                        training.getActivityType(),
                        training.getDistance(),
                        training.getAverageSpeed()
                ))
                .reduce("", String::concat);

        return """
                Hello %s,

                Here is your weekly training report.

                Period: %s - %s
                Trainings count: %d
                Total distance: %.2f km
                Total duration: %d minutes

                Training details:
                %s

                Keep going!
                FitnessTracker Team
                """.formatted(
                user.getFirstName(),
                startOfWeek,
                endOfWeek,
                trainings.size(),
                totalDistance,
                totalDurationMinutes,
                trainingDetails
        );
    }
}
