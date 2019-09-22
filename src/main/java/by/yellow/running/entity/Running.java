package by.yellow.running.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Running")
public class Running {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long RUNNING_ID;
    private double distance;
    private Date startTime;
    private Date finishTime;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @ManyToOne(targetEntity = WeeklyReport.class)
    @JoinColumn(name = "WEEKLYREPORT_ID")
    private WeeklyReport weeklyReport;

    public Running() {
    }

    public Running(long RUNNING_ID, double distance, Date startTime, Date finishTime) {
        this.RUNNING_ID = RUNNING_ID;
        this.distance = distance;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Long getRUNNING_ID() {
        return RUNNING_ID;
    }

    public void setRUNNING_ID(Long RUNNING_ID) {
        this.RUNNING_ID = RUNNING_ID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public WeeklyReport getWeeklyReport() {
        return weeklyReport;
    }

    public void setWeeklyReport(WeeklyReport weeklyReport) {
        this.weeklyReport = weeklyReport;
    }
}
