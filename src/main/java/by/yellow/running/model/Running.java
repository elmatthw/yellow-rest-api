package by.yellow.running.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Running {
    private Long runningId;
    private double distance;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime finishTime;

    public Running(double distance, LocalDateTime startTime, LocalDateTime finishTime) {
        this.distance = distance;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
}
