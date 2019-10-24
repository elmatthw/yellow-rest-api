package by.yellow.running.controller;

import by.yellow.running.model.User;
import by.yellow.running.model.WeeklyReport;
import by.yellow.running.service.RunningService;
import by.yellow.running.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RunningService runningService;

    public UserController(UserService userService, RunningService runningService) {
        this.userService = userService;
        this.runningService = runningService;
    }

    @GetMapping
    public @ResponseBody
    Collection<User> getUsers(@RequestParam int page, @RequestParam int limit){
        return userService.findAll(page, limit);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    User getUser(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity deleteUser(@PathVariable String id){
        userService.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(String.format("User %s deleted", id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/weeklyReports")
    public @ResponseBody
    List<WeeklyReport> getWeeklyReports(@PathVariable Long id, @RequestParam int page, @RequestParam int limit){
        return runningService.getWeeklyReports(id, page, limit);
    }

    @GetMapping("/{id}/weeklyReports/{weekNumber}")
    public @ResponseBody
    WeeklyReport getWeeklyReportsByWeekNumber(@PathVariable Long id, @PathVariable Long weekNumber){
        return runningService.getWeeklyReportByWeekNumber(id, weekNumber);
    }

    @GetMapping("/{id}/weeklyReports/weeks")
    public @ResponseBody
    List<WeeklyReport> getWeeklyReportsFromTo(@PathVariable Long id,
                                              @RequestParam int from,
                                              @RequestParam int to,
                                              @RequestParam int page,
                                              @RequestParam int limit){
        return runningService.getWeeklyReportsFromTo(id, from, to, page, limit);
    }
}
