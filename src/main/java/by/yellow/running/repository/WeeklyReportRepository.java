package by.yellow.running.repository;

import by.yellow.running.entity.User;
import by.yellow.running.entity.WeeklyReport;
import org.springframework.data.repository.CrudRepository;

import java.util.SortedSet;

public interface WeeklyReportRepository extends CrudRepository<WeeklyReport, Long> {
    SortedSet<WeeklyReport> findAllByUser(User user);
    WeeklyReport findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
}
