package by.yellow.running.repository;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.model.WeeklyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunningRepository extends JpaRepository<RunningEntity, Long> {

    String WEEKLY_REPORT_BY_WEEK_NUMBER = "SELECT sum(distance) as totalDistance, " +
            "avg(TIMESTAMPDIFF(minute, start_time, finish_time)) as averageTime, " +
            "sum(timestampdiff(second, start_time, finish_time)) / sum(distance) as averageSpeed," +
            "FLOOR((DATEDIFF(start_time, first_date()) + WEEKDAY(first_date() + INTERVAL 1 DAY)) / 7" +
            ") + 1 as weekNumber from running where user_id = :userId and (FLOOR((DATEDIFF(start_time, " +
            "first_date()) + WEEKDAY(first_date() + INTERVAL 1 DAY)) / 7" +
            ") + 1) = :weekNumber group by weekNumber;";
    String WEEKLY_REPORTS = "SELECT sum(distance) as totalDistance, " +
            "avg(TIMESTAMPDIFF(minute, start_time, finish_time)) as averageTime, " +
            "sum(timestampdiff(second, start_time, finish_time)) / sum(distance) as averageSpeed," +
            "FLOOR((DATEDIFF(start_time, first_date()) + WEEKDAY(first_date() + INTERVAL 1 DAY)) / 7" +
            ") + 1 as weekNumber from running where user_id = :userId group by weekNumber \n#pageable\n";
    String WEEKLY_REPORTS_FROM_TO = "SELECT sum(distance) as totalDistance, " +
            "avg(TIMESTAMPDIFF(minute, start_time, finish_time)) as averageTime, " +
            "sum(timestampdiff(second, start_time, finish_time)) / sum(distance) as averageSpeed," +
            "FLOOR((DATEDIFF(start_time, first_date()) + WEEKDAY(first_date() + INTERVAL 1 DAY)) / 7" +
            ") + 1 as weekNumber from running where user_id = :userId and " +
            "(FLOOR((DATEDIFF(start_time, first_date()) + WEEKDAY(first_date() + INTERVAL 1 DAY)) / 7) + 1)" +
            "between :from and :to " +
            "group by weekNumber \n#pageable\n";
    void deleteById(Long id);
    List<RunningEntity> findAllByUser(UserEntity userEntity, Pageable pageable);
    @Query(
            nativeQuery = true,
            value = WEEKLY_REPORT_BY_WEEK_NUMBER
    )
    WeeklyReport getWeeklyReportByWeekNumber(@Param("weekNumber") Long weekNumber, @Param("userId") Long userId);
    @Query(
            nativeQuery = true,
            value = WEEKLY_REPORTS
    )
    Page<WeeklyReport> getWeeklyReports(@Param("userId") Long userId, Pageable pageable);
    @Query(
            nativeQuery = true,
            value = WEEKLY_REPORTS_FROM_TO
    )
    List<WeeklyReport> getWeeklyReportsFromTo(@Param("userId") Long userId, Pageable pageable,
                                              @Param("from") int from, @Param("to") int to);
}
