package ir.Hw13.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person extends BaseEntity<Long>{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

//    @Column(name = "roll")
//    @Enumerated(EnumType.STRING)
//    private Roll roll;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;


}
