package ir.Hw13.dto.mapper;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.entity.Status;
import ir.Hw13.entity.Student;

public class StudentSignUpMapper implements PersonMapper<Student> {

    @Override
    public Student toEntity(PersonSignUpDto dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPassword(dto.getPassword());
        student.setStatus(Status.WAITING_FOR_SUBMIT);
        return student;
    }
}
