package ir.Hw13.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
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

    @ManyToOne(cascade = {PERSIST, MERGE},
            fetch = LAZY)
    private Teacher teacher;

    @ManyToOne(cascade = {PERSIST, MERGE},
            fetch = LAZY)
    private Course course;

    @OneToMany(mappedBy = "tests")
    private Set<TestQuestion> testQuestions;

    @OneToMany(mappedBy = "test"
            , cascade = ALL
            , fetch = LAZY)
    private Set<StudentTakeTestAttempt> studentTakeTestAttempts;

    @Column
    private Integer duration;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tests test = (Tests) o;
        return Objects.equals(getId(), test.getId()) && Objects.equals(title, test.title) && Objects.equals(description, test.description) && Objects.equals(dateTime, test.dateTime) && Objects.equals(teacher, test.teacher) && Objects.equals(course, test.course) && Objects.equals(testQuestions, test.testQuestions) && Objects.equals(studentTakeTestAttempts, test.studentTakeTestAttempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, dateTime, teacher, course, testQuestions, studentTakeTestAttempts);
    }
}
