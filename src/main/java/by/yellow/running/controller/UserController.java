package by.yellow.running.controller;

import by.yellow.running.model.User;
import by.yellow.running.model.WeeklyReport;
import by.yellow.running.service.RunningService;
import by.yellow.running.service.UserService;
import org.springframework.data.domain.Page;
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
    public @ResponseBody String deleteUser(@PathVariable String id){
        userService.deleteById(Long.parseLong(id));
        return "User deleted";
    }

    @PostMapping
    public @ResponseBody
    User addNewUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/{id}/weeklyReports")
    public @ResponseBody
    Page<WeeklyReport> getWeeklyReports(@PathVariable Long id, @RequestParam int page, @RequestParam int limit){
        return runningService.getWeeklyReports(id, page, limit);
    }

    @GetMapping("/{id}/weeklyReports/{weekNumber}")
    public @ResponseBody
    WeeklyReport getWeeklyReportsByWeekNumber(@PathVariable Long id, @PathVariable Long weekNumber){
        return runningService.getWeeklyReportByWeekNumber(weekNumber, id);
    }

    @GetMapping("/{id}/weeklyReports/weeks")
    public @ResponseBody
    List<WeeklyReport> getWeeklyReportsFromTo(@PathVariable Long id, @RequestParam int page, @RequestParam int limit,
                                  @RequestParam int from, @RequestParam int to){
        return runningService.getWeeklyReportsFromTo(id, page, limit, from, to);
    }
}
