package by.yellow.running;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.SortedSet;

public class User {

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
    private SortedSet<Running> runningSet;

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

    public SortedSet<Running> getRunningSet() {
        return runningSet;
    }

    public void setRunningSet(SortedSet<Running> runningSet) {
        this.runningSet = runningSet;
    }
}
