package by.yellow.running.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @javax.persistence.OrderBy("startTime")
    @JsonManagedReference("user_running")
    private SortedSet<Running> runningSet;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @javax.persistence.OrderBy("startDate")
    @JsonManagedReference
    private SortedSet<WeeklyReport> weeklyReportsSet;
    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    public User() {
        this.runningSet = new TreeSet<>();
        this.weeklyReportsSet = new TreeSet<>();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public SortedSet<Running> getRunningSet() {
        return runningSet;
    }

    public void addRunning(Running running) {
        running.setUser(this);
        this.runningSet.add(running);
    }

    public SortedSet<WeeklyReport> getWeeklyReportsSet() {
        return weeklyReportsSet;
    }

    public void addWeeklyReport(WeeklyReport weeklyReport) {
        weeklyReport.setUser(this);
        if (!this.weeklyReportsSet.isEmpty()) {
            weeklyReport.setWeekNumber(this.weeklyReportsSet.last().getWeekNumber() + 1);
            this.weeklyReportsSet.add(weeklyReport);
        }
        else {
            weeklyReport.setWeekNumber(1);
            this.weeklyReportsSet.add(weeklyReport);
        }
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
