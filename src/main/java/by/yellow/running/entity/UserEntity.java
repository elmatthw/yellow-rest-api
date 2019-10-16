package by.yellow.running.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.SortedSet;

@Entity
@Table(name = "user")
@Getter
@Setter

@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long Id;
    @NotNull
    @Size(min = 5, message = "Email must be at least 5 characters long")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$")
    private String email;
    @NotNull
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;
    @NotNull
    @Size(min = 5, message = "Password must be at least 8 characters long")
    private String password;
    @Transient
    private String passwordConfirm;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @javax.persistence.OrderBy("start_time")
    @JsonManagedReference("user_running")
    private SortedSet<RunningEntity> runningSet;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @javax.persistence.OrderBy("start_date")
    @JsonManagedReference
    private SortedSet<WeeklyReportEntity> weeklyReportsSet;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    /*public void addWeeklyReport(WeeklyReport weeklyReport) {
        weeklyReport.setUser(this);
        if (!this.weeklyReportsSet.isEmpty()) {
            weeklyReport.setWeekNumber(this.weeklyReportsSet.last().getWeekNumber() + 1);
            this.weeklyReportsSet.add(weeklyReport);
        }
        else {
            weeklyReport.setWeekNumber(1);
            this.weeklyReportsSet.add(weeklyReport);
        }
    }*/
}
