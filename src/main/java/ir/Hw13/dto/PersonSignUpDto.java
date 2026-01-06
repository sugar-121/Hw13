package ir.Hw13.dto;

import ir.Hw13.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonSignUpDto {

    @NotBlank(message = "You should fill first name field!")
    private String firstName;


    @NotBlank(message = "You should fill last name field!")
    private String lastName;


    @NotBlank(message = "You should fill password field!")
    @Size(message = "Password size should be more than 8 characters!", min = 8, max = 30)
    private String password;


    private Status status;
}
