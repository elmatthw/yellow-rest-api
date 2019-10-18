package by.yellow.running.service;

import by.yellow.running.model.Running;
import by.yellow.running.model.WeeklyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface RunningService {
    Running findById(long id);
    Collection<Running> findAllByUserId(Long userId, int page, int amountOfElements);
    void deleteById(long id);
    Running save(Running running);
    WeeklyReport getWeeklyReportByWeekNumber(@Param("weekNumber") Long weekNumber, @Param("userId") Long userId);
    Page<WeeklyReport> getWeeklyReports(@Param("userId") Long userId, int page, int limit);
    List<WeeklyReport> getWeeklyReportsFromTo(Long id, int page, int limit, int from, int to);
}
