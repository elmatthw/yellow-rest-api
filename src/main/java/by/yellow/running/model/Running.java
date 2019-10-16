package by.yellow.running.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Running {
    private Long id;
    private double distance;
    private Date startTime;
    private Date finishTime;
    private User user;
    private WeeklyReport weeklyReport;
}
