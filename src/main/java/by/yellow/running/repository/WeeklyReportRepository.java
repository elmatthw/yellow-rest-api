package by.yellow.running.repository;

import by.yellow.running.entity.UserEntity;
import by.yellow.running.entity.WeeklyReportEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.SortedSet;

@Repository
public interface WeeklyReportRepository extends CrudRepository<WeeklyReportEntity, Long> {
    SortedSet<WeeklyReportEntity> findAllByUser(UserEntity user);
    WeeklyReportEntity findByIdAndUser(Long id, UserEntity user);
    void deleteByIdAndUser(Long id, UserEntity user);
}
