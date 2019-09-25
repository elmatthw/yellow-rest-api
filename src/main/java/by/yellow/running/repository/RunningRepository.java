package by.yellow.running.repository;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunningRepository extends CrudRepository<Running, Long> {
    Running findByIdAndUser(Long id, User user);
    Optional<Running> findAllByUser(User user);
    void deleteByIdAndUser(Long id, User user);
}
