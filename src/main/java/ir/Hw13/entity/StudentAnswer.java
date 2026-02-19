package ir.Hw13.entity;

import jakarta.persistence.*;

import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;


@Entity
public class StudentAnswer extends BaseEntity<Long> {


    @ManyToOne(cascade = ALL
            , fetch = LAZY)
    private Questions question;

    @ManyToOne(cascade = ALL
            , fetch = LAZY)
    private StudentTakeTestAttempt attempt;

    @ManyToOne(cascade = ALL
            , fetch = LAZY)
    private Choice answeredChoice;

    @Column
    private String answeredText;


    @Column
    private Double score;


    @Column
    private boolean graded = false;


//    public void answerWithChoice(Choice choice){
//        this.selectedChoice = choice;
//        this.textAnswer = null;
//    }
//
//    public void answerWithText(String text){
//        this.textAnswer = text;
//        this.selectedChoice = null;
//    }
//
//    public void assignScore(double score){
//        this.score = score;
//        this.graded = true;
//    }

}
