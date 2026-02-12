package ir.Hw13.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
public class Tests extends BaseEntity<Long> {

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dateTime;

    @ManyToOne(cascade = {PERSIST,MERGE},
            fetch = LAZY)
    private Teacher teacher;

    @ManyToOne(cascade = {PERSIST,MERGE},
            fetch = LAZY)
    private Course course;

    @OneToMany(mappedBy = "tests")
    private Set<TestQuestion> testQuestions;

}
