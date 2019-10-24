package by.yellow.running.repository;

import by.yellow.running.entity.RunningEntity;
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
    void deleteById(Long id);
    Page<RunningEntity> findAllByUserId(Long userId, Pageable pageable);
    @Query(
            nativeQuery = true,
            value = "SELECT sum(distance) as totalDistance, " +
                    "avg(TIMESTAMPDIFF(minute, start_time, finish_time)) as averageTime, " +
                    "sum(timestampdiff(second, start_time, finish_time)) / sum(distance) as averageSpeed," +
                    "YEARWEEK(start_time) as weekNumber from running " +
                    "where user_user_id = :userId and YEARWEEK(start_time) = :weekNumber group by weekNumber;"
    )
    WeeklyReport getWeeklyReportByWeekNumber(@Param("userId") Long userId, @Param("weekNumber") Long weekNumber);
    @Query(
            nativeQuery = true,
            value = "SELECT sum(distance) as totalDistance, " +
                    "avg(TIMESTAMPDIFF(minute, start_time, finish_time)) as averageTime, " +
                    "sum(timestampdiff(second, start_time, finish_time)) / sum(distance) as averageSpeed," +
                    "YEARWEEK(start_time) as weekNumber from running where user_user_id = :userId " +
                    "group by weekNumber \n#pageable\n"
    )
    Page<WeeklyReport> getWeeklyReports(@Param("userId") Long userId, Pageable pageable);
    @Query(
            nativeQuery = true,
            value = "SELECT sum(distance) as totalDistance, " +
                    "avg(TIMESTAMPDIFF(minute, start_time, finish_time)) as averageTime, " +
                    "sum(timestampdiff(second, start_time, finish_time)) / sum(distance) as averageSpeed," +
                    "YEARWEEK(start_time) as weekNumber from running where user_user_id = :userId and " +
                    "YEARWEEK(start_time) between :from and :to group by weekNumber \n#pageable\n"
    )
    List<WeeklyReport> getWeeklyReportsFromTo(@Param("userId") Long userId, Pageable pageable,
                                              @Param("from") int fromWeekNumber, @Param("to") int toWeekNumber);
}
