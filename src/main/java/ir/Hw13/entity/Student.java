package ir.Hw13.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends Person {

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    @OneToMany(mappedBy = "student"
            , cascade = ALL
            , fetch = LAZY)
    private Set<StudentTakeTestAttempt> studentTakeTestAttempts;


}
