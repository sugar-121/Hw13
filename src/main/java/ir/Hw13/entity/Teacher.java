package ir.Hw13.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Person{

    @Column(name = "teacher_code")
    private String teacherCode;

    public Teacher(String firstName,
                   String lastName,
                   Roll roll,
                   Status status,
                   String teacherCode) {
        super(firstName, lastName, roll, status);
        this.teacherCode = teacherCode;
    }
}
