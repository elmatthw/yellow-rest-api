package by.yellow.running.controller;

import by.yellow.running.model.Running;
import by.yellow.running.service.RunningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/runnings")
public class RunningController {
    private final RunningService runningService;

    public RunningController(RunningService runningService) {
        this.runningService = runningService;
    }

    @GetMapping
    public @ResponseBody
    Iterable<Running> getRunningByUser(@RequestParam Long userId, @RequestParam int page, @RequestParam int limit){
        return runningService.findAllByUserId(userId, page, limit);
    }

    @GetMapping("/{runningId}")
    public @ResponseBody
    Running getRunningByUserAndID(@PathVariable Long runningId){
        return runningService.findById(runningId);
    }

    @PostMapping
    public @ResponseBody
    Running addRunning(@RequestParam Long userId, @RequestBody Running running){
        return runningService.create(userId, running);
    }

    @DeleteMapping("/{runningId}")
    public
    ResponseEntity deleteRunning(@PathVariable Long runningId){
        runningService.deleteById(runningId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public @ResponseBody
    Running updateRunning(@RequestBody Running newRunning){
        return runningService.update(newRunning);
    }
}
