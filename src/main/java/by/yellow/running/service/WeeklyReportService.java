package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.WeeklyReport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.SortedSet;

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
        weeklyReport.setTotalDistance(weeklyReport.getTotalDistance() + runningSet.last().getDistance());
        //weeklyReport.setAverageTime(weeklyReport.getAverageTime() + );
        return null;
    }

    private Long calculateTotalTimeSeconds(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();

        return timeDifference / 1000;
    }

    private String calculateAverage(String averageTime, long newTotalTime, int amount){
        long hours = Long.getLong(averageTime.split(":")[0]);
        long minutes = Long.getLong(averageTime.split(":")[1]);
        long seconds = Long.getLong(averageTime.split(":")[2]);

        long newAverageTime = (seconds + minutes * 60 + hours * 3600 + newTotalTime) / amount;

        //TODO: calculate new average time in hh:mm:ss

    }
}
