package ir.Hw13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Setter
@Getter
@Inheritance(strategy = SINGLE_TABLE)
public class Questions extends BaseEntity<Long> {

    @Column
    private String title;

    @Column
    private String text;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "questions")
    private Set<TestQuestion> testQuestions;

    @OneToMany(mappedBy = "question")
    private Set<StudentAnswer> studentAnswers = new HashSet<>();


    public String buildQuestionText() {
        return null;
    }
}
