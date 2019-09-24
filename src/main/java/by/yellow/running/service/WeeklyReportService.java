package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.WeeklyReport;
import org.springframework.stereotype.Service;

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
        System.out.println(weeklyReport);
        SortedSet<Running> runningSet = weeklyReport.getRunningSet();
        System.out.println(weeklyReport.getTotalDistance());
        System.out.println(runningSet.last().getDistance());
        weeklyReport.setTotalDistance(weeklyReport.getTotalDistance() + runningSet.last().getDistance());
        long totalTimeSec = calculateTotalTimeSeconds(running.getStartTime(), running.getFinishTime());
        weeklyReport.setAverageTime(
                calculateAverageTime(weeklyReport.getAverageTime(),
                        totalTimeSec,
                        runningSet.size())
        );
        if (weeklyReport.getStartDate() == null) {
            weeklyReport.setStartDate(running.getStartTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(weeklyReport.getStartDate());
            calendar.add(Calendar.DATE, 7);
            Date endDate = calendar.getTime();
            weeklyReport.setEndDate(endDate);
        }
        weeklyReport.setAverageSpeed(calculateAverageSpeed(weeklyReport.getTotalDistance(), (double)totalTimeSec));
        return weeklyReport;
    }

    private Long convertStringToSeconds(String time){
        List<String> timeArray = Arrays.asList(time.split(":"));
        long hours = Long.parseLong(timeArray.get(0));
        long minutes = Long.parseLong(timeArray.get(1));
        long seconds = Long.parseLong(timeArray.get(2));

        return seconds + minutes * 60 + hours * 3600;
    }

    private Long calculateTotalTimeSeconds(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();

        return timeDifference / 1000;
    }

    private String calculateAverageTime(String averageTime, long newTotalTime, int amount){
        long newAverageTime;
        if (averageTime != null) {
            newAverageTime = (convertStringToSeconds(averageTime) * (amount - 1) + newTotalTime) / amount;
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
