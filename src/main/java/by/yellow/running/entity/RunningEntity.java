package by.yellow.running.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "running")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RunningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "running_id")
    private Long id;
    @Column(name = "distance")
    private double distance;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "finish_time")
    private LocalDateTime finishTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
