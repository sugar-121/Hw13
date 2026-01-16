package ir.Hw13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private Set<Course> courses;

    public Teacher() {
        this.courses = new HashSet<>();
    }
}
