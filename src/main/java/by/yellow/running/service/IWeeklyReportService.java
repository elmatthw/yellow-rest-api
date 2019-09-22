package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.WeeklyReport;

public interface IWeeklyReportService {
    boolean isReportReady(WeeklyReport weeklyReport, Running running);
    WeeklyReport updateReport(WeeklyReport weeklyReport, Running running);
}
