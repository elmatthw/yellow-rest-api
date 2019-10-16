package by.yellow.running.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Role {
    private Long id;
    private String name;
    private Set<User> users;
}
