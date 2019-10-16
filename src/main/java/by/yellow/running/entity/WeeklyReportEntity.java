package by.yellow.running.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "weekly_report")
@Getter
@Setter
public class WeeklyReportEntity implements Comparable<WeeklyReportEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "weekly_report_id")
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
    private SortedSet<RunningEntity> runningSet;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    public WeeklyReportEntity() {
        this.runningSet = new TreeSet<>();
    }

    @Override
    public int compareTo(WeeklyReportEntity o) {
        if (startDate == null)
            return -1;
        return startDate.compareTo(o.startDate);
    }
}
