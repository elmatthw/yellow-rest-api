package by.yellow.running.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "running")
@Getter
@Setter
@NoArgsConstructor
public class RunningEntity implements Comparable<RunningEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "running_id")
    private Long id;
    @Column(name = "distance")
    private double distance;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_time")
    private Date finishTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user_running")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "weekly_report_id")
    @JsonBackReference("running_weekly")
    private WeeklyReportEntity weeklyReport;

    public RunningEntity(long id, double distance, Date startTime, Date finishTime) {
        this.id = id;
        this.distance = distance;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    @Override
    public int compareTo(RunningEntity o) {
        if (startTime == null)
            return -1;
        return startTime.compareTo(o.getStartTime());
    }
}
