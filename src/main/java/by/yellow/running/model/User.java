package by.yellow.running.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    private SortedSet<Running> runningSet;
    private SortedSet<WeeklyReport> weeklyReportsSet;
    private Set<Role> roles;

    public User() {
        this.runningSet = new TreeSet<>();
        this.weeklyReportsSet = new TreeSet<>();
    }
}
