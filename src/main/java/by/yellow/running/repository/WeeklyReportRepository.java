package by.yellow.running.repository;

import by.yellow.running.entity.User;
import by.yellow.running.entity.WeeklyReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.SortedSet;

@Repository
public interface WeeklyReportRepository extends CrudRepository<WeeklyReport, Long> {
    SortedSet<WeeklyReport> findAllByUser(User user);
    WeeklyReport findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
}
