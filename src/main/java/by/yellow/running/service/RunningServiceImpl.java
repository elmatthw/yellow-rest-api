package by.yellow.running.service;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.entity.UserEntity;
import by.yellow.running.exception.RunningDoesntExist;
import by.yellow.running.exception.UserDoesNotExist;
import by.yellow.running.mapper.RunningMapper;
import by.yellow.running.mapper.UserMapper;
import by.yellow.running.model.Running;
import by.yellow.running.model.WeeklyReport;
import by.yellow.running.repository.RunningRepository;
import by.yellow.running.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class RunningServiceImpl implements RunningService {
    private final RunningRepository runningRepository;
    private final RunningMapper runningMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RunningServiceImpl(RunningRepository runningRepository, RunningMapper runningMapper, UserRepository userRepository, UserMapper userMapper) {
        this.runningRepository = runningRepository;
        this.runningMapper = runningMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Running findById(long id) {
        RunningEntity runningEntity = runningRepository.findByRunningId(id)
                .orElseThrow(() -> new RunningDoesntExist(String.format("Running with id %d doesn't exist", id)));
        return runningMapper.entityToModel(runningEntity);
    }

    @Transactional
    @Override
    public Collection<Running> findAllByUserId(Long userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return runningRepository.findAllByUserUserId(userId, pageable)
                .map(runningMapper::entityToModel)
                .getContent();
    }

    @Override
    public void deleteById(long id) {
        runningRepository.deleteByRunningId(id);
    }

    @Override
    public WeeklyReport getWeeklyReportByWeekNumber(Long userId, Long weekNumber) {
        return runningRepository.getWeeklyReportByWeekNumber(userId, weekNumber);
    }

    @Override
    public List<WeeklyReport> getWeeklyReports(Long userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return runningRepository.getWeeklyReports(userId, pageable).getContent();
    }

    @Override
    public List<WeeklyReport> getWeeklyReportsFromTo(Long userId, int fromWeekNumber, int toWeekNumber, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return runningRepository.getWeeklyReportsFromTo(userId, pageable, fromWeekNumber, toWeekNumber);
    }

    @Override
    @Transactional
    public Running update(Running running) {
        RunningEntity runningEntity = runningRepository.findByRunningId(running.getRunningId()).get();
        runningEntity.setDistance(running.getDistance());
        runningEntity.setStartTime(running.getStartTime());
        runningEntity.setFinishTime(running.getFinishTime());
        return runningMapper.entityToModel(runningRepository.save(runningEntity));
    }

    @Override
    @Transactional
    public Running create(Long userId, Running running) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserDoesNotExist(String.format("User with id %d doesn't exist", userId)));
        RunningEntity runningEntity = runningMapper.modelToEntity(running, userMapper.entityToModel(userEntity));
        RunningEntity createdEntity = runningRepository.save(runningEntity);
        return runningMapper.entityToModel(createdEntity);
    }
}
