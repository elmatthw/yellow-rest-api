package by.yellow.running.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {
    private Long userId;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Running> runningSet;
    private Set<Role> roles;

    public User() {
        this.runningSet = new HashSet<>();
    }

    public User(String email, String username, String password, String passwordConfirm) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
