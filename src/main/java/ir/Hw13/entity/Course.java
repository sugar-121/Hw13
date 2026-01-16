package ir.Hw13.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

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

    @ManyToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private Teacher teacher;
}
