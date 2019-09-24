package by.yellow.running.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "WeeklyReport")
public class WeeklyReport implements Comparable<WeeklyReport> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WEEKLYREPORT_ID")
    private Long id;
    private int weekNumber;
    private double averageSpeed;
    private String averageTime;
    private double totalDistance;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @OneToMany(mappedBy = "weeklyReport", cascade = CascadeType.ALL)
    @javax.persistence.OrderBy("startTime")
    @JsonManagedReference("running_weekly")
    private SortedSet<Running> runningSet;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    public WeeklyReport() {
        this.runningSet = new TreeSet<>();
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public SortedSet<Running> getRunningSet() {
        return runningSet;
    }

    public void addRunning(Running running) {
        running.setWeeklyReport(this);
        this.runningSet.add(running);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(WeeklyReport o) {
        if (startDate == null)
            return -1;
        return startDate.compareTo(o.startDate);
    }
}
