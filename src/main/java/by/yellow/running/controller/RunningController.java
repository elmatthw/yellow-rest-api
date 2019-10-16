package by.yellow.running.controller;

import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import by.yellow.running.service.IRunningService;
import by.yellow.running.service.RunningService;
import by.yellow.running.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RunningController {

    @Autowired
    private UserService userService;
    @Autowired
    private IRunningService runningService;

    @Autowired
    public RunningController(RunningService runningService, UserService userService) {
        this.runningService = runningService;
        this.userService = userService;
    }

    @GetMapping("/users/{id}/runnings")
    public @ResponseBody
    Iterable<Running> getRunningByUser(@PathVariable String id){
        User user = userService.findById(Long.parseLong(id));
        return runningService.findAllByUser(user);
    }

    @GetMapping("/users/{userId}/runnings/{runningId}")
    public @ResponseBody
    Running getRunningByUserAndID(@PathVariable String userId, @PathVariable String runningId){
        User user = userService.findById(Long.parseLong(userId));
        return runningService.findByIdAndUser(Long.parseLong(runningId), user);
    }

    @PostMapping("/users/{userId}/runnings")
    public @ResponseBody Running addRunning(@PathVariable String userId, @RequestBody Running running){
        User user = userService.findById(Long.parseLong(userId));
        return runningService.addRunning(running);
    }

    @DeleteMapping("/users/{userId}/runnings/{runningId}")
    public @ResponseBody String deleteRunning(@PathVariable String userId, @PathVariable String runningId){
        User user = userService.findById(Long.parseLong(userId));
        runningService.deleteByIdAndUser(Long.parseLong(runningId), user);
        return "Running deleted";
    }

    @PutMapping("/users/{userId}/runnings/{runningId}")
    public @ResponseBody
    Running updateRunning(@PathVariable String userId, @PathVariable String runningId, @RequestBody Running newRunning){
        User user = userService.findById(Long.parseLong(userId));
        Running running = runningService.findByIdAndUser(Long.parseLong(runningId), user);
        runningService.save(newRunning);
        running.setDistance(newRunning.getDistance());
        running.setStartTime(newRunning.getStartTime());
        running.setFinishTime(newRunning.getFinishTime());
        return running;
    }
}
