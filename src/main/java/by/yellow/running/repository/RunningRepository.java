package by.yellow.running.repository;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RunningRepository extends CrudRepository<RunningEntity, Long> {
    RunningEntity findByIdAndUser(Long id, UserEntity user);
    Collection<RunningEntity> findAllByUser(UserEntity user);
    void deleteByIdAndUser(Long id, UserEntity user);
}
