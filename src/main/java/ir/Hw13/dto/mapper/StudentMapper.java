package ir.Hw13.dto.mapper;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;
import ir.Hw13.entity.Student;
import ir.Hw13.entity.Teacher;

public class StudentMapper {

    public Student toEntityS(PersonSignUpDto dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPassword(dto.getPassword());
        student.setStatus(Status.WAITING_FOR_SUBMIT);
        return student;
    }


    public Teacher mapStudentToTeacher(PersonUpdateDto dto, Person fetchedPerson){
        Teacher teacher = new Teacher();
        if (dto.getFirstName()!=null){
            teacher.setFirstName(dto.getFirstName());
        }else {
            teacher.setFirstName(fetchedPerson.getFirstName());
        }
        if (dto.getLastName()!=null){
            teacher.setLastName(dto.getLastName());
        }else {
            teacher.setLastName(fetchedPerson.getLastName());
        }
        teacher.setPassword(fetchedPerson.getPassword());
        teacher.setStatus(fetchedPerson.getStatus());
        return teacher;
    }
}
