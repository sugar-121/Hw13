package ir.Hw13.entity;


import jakarta.persistence.Entity;

@Entity
public class DescriptiveQuestion extends Questions {

    @Override
    public String buildQuestionText() {

        return "Question: " + getText() + "\n";
    }
}
