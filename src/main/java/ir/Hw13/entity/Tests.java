package ir.Hw13.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Tests extends BaseEntity<Long> {

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime dateTime;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Course course;

}
