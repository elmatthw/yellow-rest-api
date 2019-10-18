package by.yellow.running.service;

import by.yellow.running.entity.RunningEntity;
import by.yellow.running.exception.RunningDoesntExist;
import by.yellow.running.mapper.RunningMapper;
import by.yellow.running.mapper.UserMapper;
import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import by.yellow.running.model.WeeklyReport;
import by.yellow.running.repository.RunningRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RunningServiceImpl implements RunningService {
    private final RunningRepository runningRepository;
    private final UserServiceImpl userService;
    private final RunningMapper runningMapper;
    private final UserMapper userMapper;

    public RunningServiceImpl(RunningRepository runningRepository, UserServiceImpl userService, RunningMapper runningMapper, UserMapper userMapper) {
        this.runningRepository = runningRepository;
        this.userService = userService;
        this.runningMapper = runningMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Running findById(long id) {
        RunningEntity runningEntity = runningRepository.findById(id)
                .orElseThrow(() -> new RunningDoesntExist(String.format("Running with id %d doesn't exist", id)));
        return runningMapper.entityToModel(runningEntity);
    }

    @Transactional
    @Override
    public Collection<Running> findAllByUserId(Long userId, int page, int amountOfElements) {
        Pageable pageable = PageRequest.of(page - 1, amountOfElements);
        User user = userService.findById(userId);
        return runningRepository.findAllByUser(userMapper.modelToEntity(user), pageable)
                .stream()
                .map(runningMapper::entityToModel)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteById(long id) {
        runningRepository.deleteById(id);
    }

    @Override
    public WeeklyReport getWeeklyReportByWeekNumber(Long weekNumber, Long userId) {
        return runningRepository.getWeeklyReportByWeekNumber(weekNumber, userId);
    }

    @Override
    public Page<WeeklyReport> getWeeklyReports(Long userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return runningRepository.getWeeklyReports(userId, pageable);
    }

    @Override
    public List<WeeklyReport> getWeeklyReportsFromTo(Long id, int page, int limit, int from, int to) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return runningRepository.getWeeklyReportsFromTo(id, pageable, from, to);
    }

    @Override
    public Running save(Running running) {
        RunningEntity runningEntity = runningRepository.save(runningMapper.modelToEntity(running));
        return runningMapper.entityToModel(runningEntity);
    }
}
