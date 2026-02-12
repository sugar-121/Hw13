package ir.Hw13.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Choice extends BaseEntity<Long> {

    private String text;

    @ManyToOne
    private MultipleChoiceQuestion question;
}
