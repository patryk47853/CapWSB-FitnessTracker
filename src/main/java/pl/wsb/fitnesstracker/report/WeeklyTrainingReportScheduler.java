package pl.wsb.fitnesstracker.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler responsible for triggering weekly training reports generation.
 */
@Component
@RequiredArgsConstructor
@Slf4j
class WeeklyTrainingReportScheduler {

    private final WeeklyTrainingReportService reportService;


    /**
     * Generates training reports once a week.
     * Cron: every Monday at 08:00.
     */
//    @Scheduled(cron = "0 0 8 * * MON")
//    void generateWeeklyReports() {
//        reportService.generateWeeklyReports();
//    }

    @Scheduled(fixedRate = 30000)
    public void generateWeeklyReports() {
        reportService.generateWeeklyReports();
    }
}
