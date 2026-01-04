package ir.Hw13.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Student extends Person{

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "major")
    private String major;

    public Student(String firstName,
                   String lastName,
                   Roll roll,
                   Status status,
                   String studentCode,
                   String major) {
        super(firstName, lastName, roll, status);
        this.studentCode = studentCode;
        this.major = major;
    }


}
