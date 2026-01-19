package pl.wsb.fitnesstracker.loader;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.LocalDate.now;
import static java.util.Objects.isNull;

/**
 * Sample init data loader. If the application is run with `loadInitialData` profile, then on application startup it will fill the database with dummy data,
 * for the manual testing purposes. Loader is triggered by {@link ContextRefreshedEvent } event
 */
@Component
@Profile("loadInitialData")
@Slf4j
@ToString
class InitialDataLoader {

    private final JpaRepository<User, Long> userRepository;

    private final JpaRepository<Training, Long> trainingRepository;

    InitialDataLoader(
            final JpaRepository<User, Long> userRepository,
            final JpaRepository<Training, Long> trainingRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }

    @EventListener
    @Transactional
    @SuppressWarnings({"squid:S1854", "squid:S1481", "squid:S1192", "unused"})
    public void loadInitialData(ContextRefreshedEvent event) {
        verifyDependenciesAutowired();
        List<User> sampleUserList = generateSampleUsers();
        List<Training> sampleTrainingList = generateTrainingData(sampleUserList);

    }

    private User generateUser(String name, String lastName, int age) {
        User user = new User(name,
                lastName,
                now().minusYears(age),
                "%s.%s@domain.com".formatted(name, lastName));
        return userRepository.save(user);
    }

    private List<User> generateSampleUsers() {
        List<User> users = new ArrayList<>();

        users.add(generateUser("Emma", "Johnson", 28));
        users.add(generateUser("Ethan", "Taylor", 51));
        users.add(generateUser("Olivia", "Davis", 76));
        users.add(generateUser("Daniel", "Thomas", 34));
        users.add(generateUser("Sophia", "Baker", 49));
        users.add(generateUser("Liam", "Jones", 23));
        users.add(generateUser("Ava", "Williams", 21));
        users.add(generateUser("Noah", "Miller", 39));
        users.add(generateUser("Grace", "Anderson", 33));
        users.add(generateUser("Oliver", "Swift", 29));
        users.add(generateUser("Mikołaj", "Święty", 67));

        return users;
    }

    private List<Training> generateTrainingData(List<User> users) {
        List<Training> trainingData = new ArrayList<>();

        LocalDateTime startOfWeek = LocalDate.now()
                .minusWeeks(1)
                .with(java.time.DayOfWeek.MONDAY)
                .atStartOfDay();

        for (int i = 0; i < users.size() && i < 10; i++) {
            LocalDateTime start = startOfWeek.plusDays(i % 5).plusHours(8);
            LocalDateTime end = start.plusHours(1);

            Training training = new Training(
                    users.get(i),
                    Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(end.atZone(ZoneId.systemDefault()).toInstant()),
                    ActivityType.RUNNING,
                    10 + i,
                    8 + i * 0.5
            );

            trainingData.add(training);
        }

        trainingRepository.saveAll(trainingData);
        return trainingData;
    }

    private void verifyDependenciesAutowired() {
        if (isNull(userRepository)) {
            throw new IllegalStateException("Initial data loader was not autowired correctly " + this);
        }
    }

}
