package ir.Hw13.dto;

import ir.Hw13.entity.Roll;
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
public class PersonUpdateDto {

    private String firstName;

    private String lastName;

    private String roll;

}
