package by.yellow.running.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Running> runningSet;
    private Set<Role> roles;

    public User() {
        this.runningSet = new HashSet<>();
    }
}
