package by.yellow.running.service;

import by.yellow.running.entity.Running;

import java.util.Optional;

public interface IRunningService {
    Optional<Running> addRunning(Running running);
}
