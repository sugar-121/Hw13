package ir.Hw13.service;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.mapper.TeacherMapper;
import ir.Hw13.entity.Teacher;
import ir.Hw13.repository.TeacherRepositoryImpl;
import ir.Hw13.util.ApplicationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class TeacherServiceImpl implements BaseService {

    private Validator validator;
    private TeacherRepositoryImpl teacherRepository;
    TeacherMapper signUpTeacherMapper;


    public TeacherServiceImpl() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
        this.teacherRepository = ApplicationContext.getInstance().getTeacherRepository();
        this.signUpTeacherMapper = ApplicationContext.getInstance().getSignUpTeacherMapper();
    }

    @Override
    public boolean signUp(PersonSignUpDto dto) {
        Set<ConstraintViolation<PersonSignUpDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(v.getMessage()));
            return false;
        }
        Teacher teacher = signUpTeacherMapper.toEntityT(dto);
        teacherRepository.signUp(teacher);
        return true;
    }
}
