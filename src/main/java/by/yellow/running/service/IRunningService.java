package by.yellow.running.service;

import by.yellow.running.model.Running;
import by.yellow.running.model.User;

import java.util.Collection;

public interface IRunningService {
    Running addRunning(Running running);

    Running findByIdAndUser(long id, User user);

    Collection<Running> findAllByUser(User user);

    void deleteByIdAndUser(long id, User user);

    void save(Running newRunning);
}
