package by.yellow.running.controller;

import by.yellow.running.model.Running;
import by.yellow.running.model.User;
import by.yellow.running.service.RunningService;
import by.yellow.running.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RunningController {

    private final UserService userService;
    private final RunningService runningService;

    public RunningController(UserService userService, RunningService runningService) {
        this.userService = userService;
        this.runningService = runningService;
    }

    @GetMapping("/users/{userId}/runnings")
    public @ResponseBody
    Iterable<Running> getRunningByUser(@PathVariable Long userId, @RequestParam int page, @RequestParam int limit){
        return runningService.findAllByUserId(userId, page, limit);
    }

    @GetMapping("/users/runnings/{runningId}")
    public @ResponseBody
    Running getRunningByUserAndID(@PathVariable Long runningId){
        return runningService.findById(runningId);
    }

    @PostMapping("/users/runnings")
    public @ResponseBody Running addRunning(@PathVariable String userId, @RequestBody Running running){
        User user = userService.findById(Long.parseLong(userId));
        return runningService.save(running);
    }

    @DeleteMapping("/users/runnings/{runningId}")
    public @ResponseBody String deleteRunning(@PathVariable Long userId, @PathVariable Long runningId){
        runningService.deleteById(runningId);
        return "Running deleted";
    }

    @PutMapping("/users/runnings/{runningId}")
    public @ResponseBody
    Running updateRunning(@PathVariable Long userId, @PathVariable Long runningId, @RequestBody Running newRunning){
        Running running = runningService.findById(runningId);
        runningService.save(newRunning);
        running.setDistance(newRunning.getDistance());
        running.setStartTime(newRunning.getStartTime());
        running.setFinishTime(newRunning.getFinishTime());
        return running;
    }
}
