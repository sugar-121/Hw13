package ir.Hw13.dto.mapper;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.entity.*;

import java.util.Set;

public class TeacherMapper{


    public Teacher toEntityT(PersonSignUpDto dto) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setPassword(dto.getPassword());
        teacher.setStatus(Status.WAITING_FOR_SUBMIT);
        return teacher;
    }

    public Student mapTeacherToStudent(PersonUpdateDto dto, Person fetchedPerson){
        Student student = new Student();
        if (dto.getFirstName()!=null){
            student.setFirstName(dto.getFirstName());
        }else {
            student.setFirstName(fetchedPerson.getFirstName());
        }
        if (dto.getLastName()!=null){
            student.setLastName(dto.getLastName());
        }else {
            student.setLastName(fetchedPerson.getLastName());
        }
        student.setPassword(fetchedPerson.getPassword());
        student.setStatus(fetchedPerson.getStatus());
        return student;
    }
}
