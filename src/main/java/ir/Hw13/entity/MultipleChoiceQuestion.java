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

}
