package ir.Hw13.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity<Long> {

    @Column
    private String title;

    @Column
    private LocalDate begin;

    @Column
    private LocalDate finish;

    @ManyToMany
    private Set<Student> students;

    @ManyToOne(cascade = {PERSIST,MERGE},
            fetch = LAZY)
    private Teacher teacher;
}
