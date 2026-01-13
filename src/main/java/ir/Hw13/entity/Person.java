package ir.Hw13.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person extends BaseEntity<Long>{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

//    @Column(name = "roll")
//    @Enumerated(EnumType.STRING)
//    private Roll roll;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;



}
