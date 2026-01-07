package ir.Hw13.dto.mapper;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.entity.Status;
import ir.Hw13.entity.Teacher;

public class TeacherMapper{


    public Teacher toEntityT(PersonSignUpDto dto) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setPassword(dto.getPassword());
        teacher.setStatus(Status.WAITING_FOR_SUBMIT);
        return teacher;
    }
}
