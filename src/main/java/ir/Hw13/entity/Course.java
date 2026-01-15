package ir.Hw13.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course  extends BaseEntity<Long>{

    @Column
    private String title;

    @Column
    private LocalDateTime begin;

    @Column
    private LocalDateTime finish;

    @ManyToMany
    private Set<Student> students;

    @ManyToOne
    private Teacher teacher;
}
