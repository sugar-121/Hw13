package ir.Hw13.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "test_id"})
        })
@Getter
@Setter
@NoArgsConstructor
public class StudentTakeTestAttempt extends BaseEntity<Long> {

    @ManyToOne(fetch = LAZY)
    private Student student;

    @ManyToOne(fetch = LAZY)
    private Tests test;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column
    private TakingStatus status;

    @Column
    private Double totalScore;

    @OneToMany(mappedBy = "attempt"
            , cascade = ALL)
    private Set<StudentAnswer> answers = new HashSet<>();

//    public void addAnswer(StudentAnswer answer){
//        answers.add(answer);
//        answer.setAttempt(this);
//    }
//
//    public void finishAttempt(){
//        this.status = AttemptStatus.FINISHED;
//        this.endTime = LocalDateTime.now();
//    }
//
//    public void autoFinish(){
//        this.status = AttemptStatus.AUTO_FINISHED;
//        this.endTime = LocalDateTime.now();
//    }


}
