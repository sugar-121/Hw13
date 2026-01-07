package ir.Hw13.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor

public class Manager extends Person{
    public Manager(String firstName,
                   String lastName,
                   String password,
                   Status status) {
        super(firstName, lastName, password, status);
    }
}
