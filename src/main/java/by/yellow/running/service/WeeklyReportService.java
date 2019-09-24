package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.WeeklyReport;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class WeeklyReportService implements IWeeklyReportService{

    @Override
    public boolean isReportReady(WeeklyReport weeklyReport, Running running) {
        Date runningStartTime = running.getStartTime();
        Date reportEndTime = weeklyReport.getEndDate();
        long timeDifference = reportEndTime.getTime() - runningStartTime.getTime();

        return timeDifference > 0;
    }

    @Override
    public WeeklyReport updateReport(WeeklyReport weeklyReport, Running running) {
        weeklyReport.addRunning(running);
        SortedSet<Running> runningSet = weeklyReport.getRunningSet();
        weeklyReport.setTotalDistance(calculateTotalDistance(weeklyReport));
        long totalTimeSeconds = calculateTotalTimeInSeconds(weeklyReport);
        weeklyReport.setAverageTime(
                convertSecondsToString(
                        calculateAverageTimeSeconds(totalTimeSeconds, runningSet.size())
                )
        );
        if (weeklyReport.getStartDate() == null) {
            weeklyReport.setStartDate(running.getStartTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(weeklyReport.getStartDate());
            calendar.add(Calendar.DATE, 7);
            Date endDate = calendar.getTime();
            weeklyReport.setEndDate(endDate);
        }
        weeklyReport.setAverageSpeed(calculateAverageSpeed(weeklyReport.getTotalDistance(), totalTimeSeconds));
        return weeklyReport;
    }

    private Double calculateTotalDistance(WeeklyReport weeklyReport){
        List<Double> list = new ArrayList<>();
        for (Running running: weeklyReport.getRunningSet()) {
            list.add(running.getDistance());
        }
        return list.stream()
                .reduce(0.0, Double::sum);
    }

    private Long calculateTotalTimeInSeconds(WeeklyReport weeklyReport){
        List<Long> list = new ArrayList<>();
        for (Running running: weeklyReport.getRunningSet()) {
            list.add(running.getFinishTime().getTime() -  running.getStartTime().getTime());
        }
        return list.stream()
                .reduce(0L, Long::sum) / 1000;
    }

    private String convertSecondsToString(Long time){
        LocalTime timeOfDay = LocalTime.ofSecondOfDay(time);
        return timeOfDay.toString();
    }

    private Long calculateAverageTimeSeconds(Long averageTime, int amount){
        return averageTime / amount;
    }

    private double calculateAverageSpeed(double totalDistance, double totalTime){
        return totalDistance / totalTime;
    }
}
