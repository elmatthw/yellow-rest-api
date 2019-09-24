package by.yellow.running.service;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.User;
import by.yellow.running.entity.WeeklyReport;
import by.yellow.running.repository.RunningRepository;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.repository.WeeklyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;

@Service
public class RunningService implements IRunningService{
    @Autowired
    private RunningRepository runningRepository;
    @Autowired
    private IWeeklyReportService weeklyReportService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeeklyReportRepository weeklyReportRepository;

    @Override
    public Optional<Running> addRunning(Running running) {
        User user = running.getUser();
        SortedSet<WeeklyReport> weeklyReports = user.getWeeklyReportsSet();
        if (!weeklyReports.isEmpty() && weeklyReportService.isReportReady(weeklyReports.last(), running)) {
            WeeklyReport weeklyReport = weeklyReportService.updateReport(weeklyReports.last(), running);
            weeklyReportRepository.save(weeklyReport);
        }
        else {
            WeeklyReport weeklyReport = new WeeklyReport();
            user.addWeeklyReport(weeklyReportService.updateReport(weeklyReport, running));
            user.addWeeklyReport(weeklyReport);
            /*weeklyReport.setUser(user);*/
            weeklyReportRepository.save(weeklyReport);
        }
        userRepository.save(user);
        return runningRepository.findById(runningRepository.save(running).getid());
    }
}
