package ir.Hw13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Getter
@Setter
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher",
            cascade = {PERSIST, MERGE},
            fetch = FetchType.LAZY)
    private Set<Course> courses;

    @OneToMany(mappedBy = "teacher")
    private Set<Questions> questions;


    public Teacher() {
        this.courses = new HashSet<>();
        this.questions = new HashSet<>();
    }
}
