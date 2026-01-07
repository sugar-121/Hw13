package ir.Hw13.service;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.mapper.StudentMapper;
import ir.Hw13.entity.Student;
import ir.Hw13.repository.StudentRepositoryImpl;
import ir.Hw13.util.ApplicationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class StudentServiceImpl implements BaseService {

    private Validator validator;
    private StudentRepositoryImpl studentRepository;
    private StudentMapper studentMapper;

    public StudentServiceImpl() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
        this.studentRepository = ApplicationContext.getInstance().getStudentRepository();
        this.studentMapper = ApplicationContext.getInstance().getStudentMapper();
    }

    @Override
    public boolean signUp(PersonSignUpDto dto) {
        Set<ConstraintViolation<PersonSignUpDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()){
            violations.forEach(v-> System.out.println(v.getMessage()));
            return false;
        }
        Student student = studentMapper.toEntityS(dto);
        studentRepository.signUp(student);
        return true;
    }
}
