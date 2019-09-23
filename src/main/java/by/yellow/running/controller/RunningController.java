package by.yellow.running.controller;

import by.yellow.running.entity.Running;
import by.yellow.running.entity.User;
import by.yellow.running.repository.RunningRepository;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.service.IRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
//@SessionAttributes("user")
@RequestMapping
public class RunningController {

    private RunningRepository runningRepository;
    private UserRepository userRepository;
    @Autowired
    private IRunningService runningService;

    @Autowired
    public RunningController(RunningRepository runningRepository, UserRepository userRepository) {
        this.runningRepository = runningRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{id}/running")
    public @ResponseBody
    Optional<Running> getRunningByUser(@PathVariable String id){
        User user = userRepository.findById(Long.parseLong(id)).get();
        return runningRepository.findAllByUser(user);
    }

    @GetMapping("/users/{user_id}/running/{running_id}")
    public @ResponseBody Running getRunningByUserAndID(@PathVariable String user_id, @PathVariable String running_id){
        User user = userRepository.findById(Long.parseLong(user_id)).get();
        return runningRepository.findByIdAndUser(Long.parseLong(running_id), user);
    }

    @PostMapping("/users/{user_id}/running")
    public @ResponseBody Running addRunning(@PathVariable String user_id, @RequestBody Running running){
        System.out.println("here");
        User user = userRepository.findById(Long.parseLong(user_id)).get();
        user.addRunning(running);
        return runningService.addRunning(running);
    }

    @DeleteMapping("/users/{user_id}/running/{running_id}")
    public @ResponseBody String deleteRunning(@PathVariable String user_id, @PathVariable String running_id){
        User user = userRepository.findById(Long.parseLong(user_id)).get();
        runningRepository.deleteByIdAndUser(Long.parseLong(running_id), user);
        return "Running deleted";
    }

    @PutMapping("/users/{user_id}/running/{running_id}")
    public @ResponseBody Running updateRunning(@PathVariable String user_id, @PathVariable String running_id, @RequestBody Running newRunning){
        User user = userRepository.findById(Long.parseLong(user_id)).get();
        Running running = runningRepository.findByIdAndUser(Long.parseLong(running_id), user);
        runningRepository.save(newRunning);
        running.setDistance(newRunning.getDistance());
        running.setStartTime(newRunning.getStartTime());
        running.setFinishTime(newRunning.getFinishTime());
        return running;
    }
}
