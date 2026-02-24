package ir.Hw13.service;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.mapper.CourseMapper;
import ir.Hw13.dto.mapper.StudentMapper;
import ir.Hw13.entity.*;
import ir.Hw13.repository.StudentRepositoryImpl;
import ir.Hw13.repository.TestRepository;
import ir.Hw13.service.exceptions.AlreadyTaken;
import ir.Hw13.service.exceptions.TimeIsUp;
import ir.Hw13.util.ApplicationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class StudentServiceImpl implements BaseService {

    private final Validator validator;
    private final StudentRepositoryImpl studentRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final TestService testService;
    private final TestRepository testRepository;

    public StudentServiceImpl() {
        this.validator = ApplicationContext.getInstance().getValidator();
        this.studentRepository = ApplicationContext.getInstance().getStudentRepository();
        this.studentMapper = ApplicationContext.getInstance().getStudentMapper();
        this.courseMapper = ApplicationContext.getInstance().getCourseMapper();
        this.testService = ApplicationContext.getInstance().getTestService();
        this.testRepository = ApplicationContext.getInstance().getTestRepository();
    }

    @Override
    public boolean signUp(PersonSignUpDto dto) {
        Set<ConstraintViolation<PersonSignUpDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(v.getMessage()));
            return false;
        }
        Student student = studentMapper.toEntityS(dto);
        studentRepository.save(student);
        return true;
    }

    public boolean logIn(long id, String password) {
        return studentRepository.logIn(id, password);
    }

    public void showStudentCourses(long studentId) {
        List<Course> courses = studentRepository.loadStudentCourses(studentId);
        for (Course course : courses) {
            courseMapper.loadCourse(course);
        }
    }

    public Student loadStudentById(long studentId) {
        return studentRepository.loadStudentById(studentId);
    }

    public StudentTakeTestAttempt takeTest(long studentId, Tests test) {
        Student student = loadStudentById(studentId);


        Set<StudentTakeTestAttempt> studentTakeTestAttempts = student.getStudentTakeTestAttempts();
        for (StudentTakeTestAttempt attempt : studentTakeTestAttempts) {
            if (attempt.getTest().equals(test)) {
                if (attempt.getStatus() == TakingStatus.FINISHED) {
                    throw new AlreadyTaken();
                } else if (attempt.getStatus() == TakingStatus.IN_PROGRESS) {
                    if (testService.getRemainingTime(attempt).isPositive()){
                        return attempt;
                    }else {
                        testService.finishTest(attempt);
                        throw new TimeIsUp();
                    }
                }
            }
        }
        StudentTakeTestAttempt attempt = new StudentTakeTestAttempt();
        attempt.setStudent(student);
        attempt.setTest(test);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus(TakingStatus.IN_PROGRESS);
        student.getStudentTakeTestAttempts().add(attempt);

        testRepository.takeTest(attempt);
        return attempt;
    }
}
