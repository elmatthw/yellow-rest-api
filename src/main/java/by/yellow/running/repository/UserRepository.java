package by.yellow.running.repository;

import by.yellow.running.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    //User findUserByUSER_ID(Long id);

    Optional<User> findByUsername(String username);
}
