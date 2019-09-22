package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.User;
import by.yellow.running.entity.WeeklyReport;
import by.yellow.running.repository.RunningRepository;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.repository.WeeklyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedSet;

@Service
public class RunningService implements IRunningService{
    @Autowired
    private RunningRepository runningRepository;
    @Autowired
    private WeeklyReportRepository weeklyReportRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addRunning(Running running) {
        User user = running.getUser();
        SortedSet<WeeklyReport> weeklyReports = user.getWeeklyReportsSet();
        if (weeklyReports == null) {
            WeeklyReport weeklyReport = new WeeklyReport();
        }

    }
}
