package by.yellow.running.service;

import by.yellow.running.model.Running;
import by.yellow.running.model.WeeklyReport;

public interface IWeeklyReportService {
    boolean isReportReady(WeeklyReport weeklyReport, Running running);
    WeeklyReport updateReport(WeeklyReport weeklyReport, Running running);
    WeeklyReport save(WeeklyReport weeklyReport);
}
