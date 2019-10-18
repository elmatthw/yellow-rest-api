package by.yellow.running.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Running {
    private Long id;
    private double distance;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
}
