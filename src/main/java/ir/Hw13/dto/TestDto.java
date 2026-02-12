package ir.Hw13.dto;


import ir.Hw13.entity.Course;
import ir.Hw13.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TestDto {
    private String title;
    private String description;
    private LocalDate date;
    private Teacher teacher;
    private Course course;
}
