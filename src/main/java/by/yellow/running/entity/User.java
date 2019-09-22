package by.yellow.running.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.SortedSet;

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
    @OneToMany(targetEntity = Running.class, mappedBy = "user", fetch=FetchType.EAGER)
    @javax.persistence.OrderBy("startTime")
    private SortedSet<Running> runningSet;
    @OneToMany(targetEntity = WeeklyReport.class, mappedBy = "user", fetch=FetchType.EAGER)
    private SortedSet<WeeklyReport> weeklyReportsSet;
    @ManyToMany
    private Set<Role> roles;

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
        this.weeklyReportsSet.add(weeklyReport);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
