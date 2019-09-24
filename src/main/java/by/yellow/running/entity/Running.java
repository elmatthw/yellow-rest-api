package by.yellow.running.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

@Entity
@Table(name = "Running")
public class Running implements Comparable<Running> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RUNNING_ID")
    private Long id;
    @Column(name = "distance")
    private double distance;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finishTime")
    private Date finishTime;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference("user_running")
    private User user;
    @ManyToOne
    @JoinColumn(name = "WEEKLYREPORT_ID")
    @JsonBackReference("running_weekly")
    private WeeklyReport weeklyReport;

    public Running() {
    }

    public Running(long id, double distance, Date startTime, Date finishTime) {
        this.id = id;
        this.distance = distance;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
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


    @Override
    public int compareTo(Running o) {
        if (startTime == null)
            return -1;
        return startTime.compareTo(o.getStartTime());
    }
}
