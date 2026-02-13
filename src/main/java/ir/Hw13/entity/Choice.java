package ir.Hw13.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Choice extends BaseEntity<Long> {

    private String text;

    @ManyToOne(cascade = {PERSIST, MERGE})
    private MultipleChoiceQuestion question;
}
