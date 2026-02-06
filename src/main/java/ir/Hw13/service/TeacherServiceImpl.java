package ir.Hw13.service;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.mapper.CourseMapper;
import ir.Hw13.dto.mapper.TeacherMapper;
import ir.Hw13.entity.Course;
import ir.Hw13.entity.Teacher;
import ir.Hw13.repository.TeacherRepositoryImpl;
import ir.Hw13.util.ApplicationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

public class TeacherServiceImpl implements BaseService {

    private final Validator validator;
    private final TeacherRepositoryImpl teacherRepository;
    TeacherMapper signUpTeacherMapper;
    CourseMapper courseMapper;


    public TeacherServiceImpl() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
        this.teacherRepository = ApplicationContext.getInstance().getTeacherRepository();
        this.signUpTeacherMapper = ApplicationContext.getInstance().getTeacherMapper();
        this.courseMapper = ApplicationContext.getInstance().getCourseMapper();
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

    public boolean logIn(long id, String password) {
        return teacherRepository.logIn(id, password);
    }

    public void showTeacherCourses(long id) {
        List<Course> courses = teacherRepository.showTeacherCourses(id);
        for (Course course : courses) {
            courseMapper.loadCourse(course);
        }
    }
}
