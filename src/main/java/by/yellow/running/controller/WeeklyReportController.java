package by.yellow.running.controller;

import by.yellow.running.entity.User;
import by.yellow.running.entity.WeeklyReport;
import by.yellow.running.repository.UserRepository;
import by.yellow.running.repository.WeeklyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.SortedSet;

@Controller
@RequestMapping
public class WeeklyReportController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeeklyReportRepository weeklyReportRepository;

    @GetMapping("/users/{id}/weeklyReports")
    public @ResponseBody SortedSet<WeeklyReport> getWeeklyReports(@PathVariable String id){
        User user = userRepository.findById(Long.parseLong(id)).get();
        return weeklyReportRepository.findAllByUser(user);
    }

    @GetMapping("/users/{user_id}/weeklyReports/{report_id}")
    public @ResponseBody WeeklyReport getWeeklyReport(@PathVariable String user_id, @PathVariable String report_id){
        User user = userRepository.findById(Long.parseLong(user_id)).get();
        return weeklyReportRepository.findByIdAndUser(Long.parseLong(report_id), user);
    }

    @DeleteMapping("/users/{user_id}/weeklyReports/{report_id}")
    public @ResponseBody String deleteWeeklyReport(@PathVariable String user_id, @PathVariable String report_id){
        User user = userRepository.findById(Long.parseLong(user_id)).get();
        weeklyReportRepository.deleteByIdAndUser(Long.parseLong(report_id), user);
        return "Weekly report deleted";
    }
}
