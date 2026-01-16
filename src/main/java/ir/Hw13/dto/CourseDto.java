package ir.Hw13.dto;

import ir.Hw13.entity.Student;
import ir.Hw13.entity.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
public class CourseDto {

    @NotBlank(message = "You should fill title field!")
    private final String title;

    @NotNull(message = "You should fill beginning date field!")
    private final LocalDate begin;

    @NotNull(message = "You should fill ending date field!")
    private final LocalDate finish;

    private Set<Student> students;

    private Teacher teacher;

}
