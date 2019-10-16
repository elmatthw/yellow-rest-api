package by.yellow.running.service;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.mapper.RunningMapper;
import by.yellow.running.mapper.UserMapper;
import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import by.yellow.running.model.WeeklyReport;
import by.yellow.running.repository.RunningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.SortedSet;

@Service
public class RunningService implements IRunningService{
    @Autowired
    private RunningRepository runningRepository;
    @Autowired
    private IWeeklyReportService weeklyReportService;
    @Autowired
    private UserService userService;

    @Override
    public Running addRunning(Running running) {
        User user = running.getUser();
        SortedSet<WeeklyReport> weeklyReports = user.getWeeklyReportsSet();
        if (!weeklyReports.isEmpty() && weeklyReportService.isReportReady(weeklyReports.last(), running)) {
            WeeklyReport weeklyReport = weeklyReportService.updateReport(weeklyReports.last(), running);
            weeklyReportService.save(weeklyReport);
        }
        else {
            WeeklyReport weeklyReport = new WeeklyReport();
            user.getWeeklyReportsSet().add(weeklyReportService.updateReport(weeklyReport, running));
            weeklyReportService.save(weeklyReport);
        }
        userService.save(user);
        save(running);
        return running;
    }

    @Override
    public Running findByIdAndUser(long id, User user) {
        RunningEntity runningEntity = runningRepository.findByIdAndUser(id, UserMapper.INSTANCE.modelToEntity(user));
        return RunningMapper.INSTANCE.entityToModel(runningEntity);
    }

    @Override
    public Collection<Running> findAllByUser(User user) {
        Collection<Running> runnings = null;
        for (RunningEntity runningEntity: runningRepository.findAll()) {
            runnings.add(RunningMapper.INSTANCE.entityToModel(runningEntity));
        }
        return runnings;
    }

    @Override
    public void deleteByIdAndUser(long id, User user) {
        runningRepository.deleteByIdAndUser(id, UserMapper.INSTANCE.modelToEntity(user));
    }

    @Override
    public void save(Running newRunning) {
        runningRepository.save(RunningMapper.INSTANCE.modelToEntity(newRunning));
    }
}
