package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.WeeklyReport;
import by.yellow.running.repository.WeeklyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.SortedSet;

@Service
public class WeeklyReportService implements IWeeklyReportService{
    @Autowired
    private WeeklyReportRepository weeklyReportRepository;

// TODO: rename method - not obvious what it's doing
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
        weeklyReport.setTotalDistance(weeklyReport.getTotalDistance() + runningSet.last().getDistance());
        long totalTimeSec = calculateTotalTimeSeconds(running.getStartTime(), running.getFinishTime());
        weeklyReport.setAverageTime(
                calculateAverageTime(weeklyReport.getAverageTime(),
                        totalTimeSec,
                        runningSet.size())
        );
        weeklyReport.setEndDate(running.getStartTime());
        weeklyReport.setTotalDistance(calculateAverageSpeed(weeklyReport.getTotalDistance(), (double)totalTimeSec));
        return weeklyReport;
    }

    private Long calculateTotalTimeSeconds(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();

        return timeDifference / 1000;
    }

    private String calculateAverageTime(String averageTime, long newTotalTime, int amount){
        long newAverageTime;
        if (averageTime != null) {
            long hours = Long.getLong(averageTime.split(":")[0]);
            long minutes = Long.getLong(averageTime.split(":")[1]);
            long seconds = Long.getLong(averageTime.split(":")[2]);
            newAverageTime = ((seconds + minutes * 60 + hours * 3600) * (amount - 1) + newTotalTime) / amount;
        }
        else
            newAverageTime = newTotalTime;


        String newAverageTimeString = String.join(":",
                new String[]{String.valueOf((int) newAverageTime / 3600), String.valueOf((int) newAverageTime / 60 % 60),
                        String.valueOf((int) newAverageTime % 60)});
        return newAverageTimeString;
    }

    private double calculateAverageSpeed(double totalDistance, double totalTime){
        return totalDistance / totalTime;
    }
}
