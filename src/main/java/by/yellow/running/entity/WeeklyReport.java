package by.yellow.running.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.SortedSet;

@Entity
@Table(name = "WeeklyReport")
public class WeeklyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long WEEKLYREPORT_ID;
    /*@Column("")
    private int weekNumber;*/
    private double averageSpeed;
    private String averageTime;
    private double totalDistance;
    private Date startDate;
    private Date endDate;
    @OneToMany(targetEntity = Running.class, mappedBy = "weeklyReport", fetch=FetchType.EAGER)
    @javax.persistence.OrderBy("startTime")
    private SortedSet<Running> runningSet;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public WeeklyReport() {

    }

    public WeeklyReport(Long WEEKLYREPORT_ID, /*int weekNumber, */double averageSpeed, String averageTime, double totalDistance, SortedSet<Running> runningSet) {
        this.WEEKLYREPORT_ID = WEEKLYREPORT_ID;
        /*this.weekNumber = weekNumber;*/
        this.averageSpeed = averageSpeed;
        this.averageTime = averageTime;
        this.totalDistance = totalDistance;
        this.runningSet = runningSet;
    }

    public Long getWEEKLYREPORT_ID() {
        return WEEKLYREPORT_ID;
    }

    public void setWEEKLYREPORT_ID(Long WEEKLYREPORT_ID) {
        this.WEEKLYREPORT_ID = WEEKLYREPORT_ID;
    }

/*    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }*/

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
}
