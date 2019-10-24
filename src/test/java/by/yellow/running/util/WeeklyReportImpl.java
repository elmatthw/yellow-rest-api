package by.yellow.running.util;

import by.yellow.running.model.WeeklyReport;

public class WeeklyReportImpl implements WeeklyReport {
    private Long weekNumber;
    private Double totalDistance;
    private Double averageTime;
    private Double averageSpeed;

    public void setWeekNumber(Long weekNumber) {
        this.weekNumber = weekNumber;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setAverageTime(Double averageTime) {
        this.averageTime = averageTime;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public Long getWeekNumber() {
        return weekNumber;
    }

    @Override
    public Double getTotalDistance() {
        return totalDistance;
    }

    @Override
    public Double getAverageTime() {
        return averageTime;
    }

    @Override
    public Double getAverageSpeed() {
        return averageSpeed;
    }
}
