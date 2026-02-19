package ir.Hw13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class MultipleChoiceQuestion extends Questions{

    @OneToMany(mappedBy = "question")
    private Set<Choice> choices = new HashSet<>();

    @ManyToOne
    private Choice answer;


    @Override
    public String buildQuestionText() {
        int count = 1;
        StringBuilder builder = new StringBuilder();


        builder.append("Question id ")
                .append(getId())
                .append(": ")
                .append(getText())
                .append("\n");

        for (Choice choice : getChoices()) {
            builder.append(count)
                    .append(")")
                    .append(choice.getText())
                    .append("\n");
            count++;
        }
           return builder.toString();
    }
}
