package by.yellow.running.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ROLE_ID;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Long getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(Long ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
