package by.yellow.running.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@Setter
public class WeeklyReport {
    private Long id;
    private int weekNumber;
    private double averageSpeed;
    private String averageTime;
    private double totalDistance;
    private Date startDate;
    private Date endDate;
    private SortedSet<Running> runningSet;
    private User user;

    public WeeklyReport() {
        this.runningSet = new TreeSet<>();
    }
}
