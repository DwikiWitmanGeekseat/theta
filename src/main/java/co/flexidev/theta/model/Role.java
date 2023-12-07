package co.flexidev.theta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Role extends BaseModel {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_roles",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @JsonIgnoreProperties("roles")
    private Collection<Person> persons = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Person> getPersons() {
        return persons;
    }

    public void setPersons(Collection<Person> persons) {
        this.persons = persons;
    }
}
