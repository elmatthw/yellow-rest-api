package by.yellow.running.repository;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.SortedSet;

@Repository
public interface RunningRepository extends CrudRepository<Running, Long> {
    SortedSet<Running> findAllByStartTimeBetween(Date firstDate, Date secondDate);
    Running findByIdAndUser(Long id, User user);
    Optional<Running> findAllByUser(User user);
    void deleteByIdAndUser(Long id, User user);
}
