package ir.Hw13.service;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.TestDto;
import ir.Hw13.dto.mapper.CourseMapper;
import ir.Hw13.dto.mapper.TeacherMapper;
import ir.Hw13.dto.mapper.TestMapper;
import ir.Hw13.entity.*;
import ir.Hw13.repository.ManagerRepository;
import ir.Hw13.repository.TeacherRepositoryImpl;
import ir.Hw13.util.ApplicationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeacherServiceImpl implements BaseService {

    private final Validator validator;
    private final TeacherRepositoryImpl teacherRepository;
    private final TeacherMapper signUpTeacherMapper;
    private final CourseMapper courseMapper;
    private final TestMapper testMapper;
    private final ManagerRepository managerRepository;
    private final ManagerService managerService;


    public TeacherServiceImpl() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
        this.teacherRepository = ApplicationContext.getInstance().getTeacherRepository();
        this.signUpTeacherMapper = ApplicationContext.getInstance().getTeacherMapper();
        this.courseMapper = ApplicationContext.getInstance().getCourseMapper();
        this.testMapper = ApplicationContext.getInstance().getTestMapper();
        this.managerRepository = ApplicationContext.getInstance().getManagerRepository();
        this.managerService = ApplicationContext.getInstance().getManagerService();
    }

    public TeacherServiceImpl( TeacherRepositoryImpl teacherRepository, Validator validator){
        this.teacherRepository = teacherRepository;
        this.validator = validator;

        this.signUpTeacherMapper = ApplicationContext.getInstance().getTeacherMapper();
        this.courseMapper = ApplicationContext.getInstance().getCourseMapper();
        this.testMapper = ApplicationContext.getInstance().getTestMapper();
       this.managerRepository = null;
        this.managerService = null;
    }

    @Override
    public boolean signUp(PersonSignUpDto dto) {
        Set<ConstraintViolation<PersonSignUpDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(v.getMessage()));
            return false;
        }
        Teacher teacher = signUpTeacherMapper.toEntityT(dto);
        teacherRepository.save(teacher);
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

    public void showTeacherTests(long teacherId, long courseId) {
        List<Tests> fetchedTeacherTests = teacherRepository.fetchTeacherTests(teacherId, courseId);
        for (Tests test : fetchedTeacherTests) {
            testMapper.loadTest(test);
        }
    }

    public void AddTest(TestDto dto) {
        teacherRepository.AddTest(testMapper.toEntity(dto));

    }

    public Choice makeChoice(String text) {
        Choice choice = new Choice();
        choice.setText(text);
        teacherRepository.makeChoice(choice);
        return choice;
    }

    public MultipleChoiceQuestion makeMCQs(long teacherId, long courseId, String title, String text, Map<Choice, Boolean> choiceList) {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        Teacher teacher = (Teacher) managerService.loadPersonById(teacherId);
        Course course = managerService.loadCourseById(courseId);
        mcq.setTeacher(teacher);
        mcq.setCourse(course);
        mcq.setText(text);
        mcq.setTitle(title);
        choiceList.forEach((k, v) -> {
            k.setQuestion(mcq);
            if (v){
                mcq.setAnswer(k);
            }
        });

        teacherRepository.addMCQ(mcq);
        return mcq;
    }

        public void addQToTest(Questions question, Tests test) {
            TestQuestion testQuestion = new TestQuestion();
            testQuestion.setQuestions(question);
            testQuestion.setTests(test);
            teacherRepository.addQToTest(testQuestion);
        }

    public Tests loadTestById(long id){
        return teacherRepository.loadTestById(id);
    }

    public DescriptiveQuestion makeDQs(long teacherId, long courseId, String title, String text) {
        DescriptiveQuestion dq = new DescriptiveQuestion();
        Teacher teacher = (Teacher) managerService.loadPersonById(teacherId);
        Course course = managerService.loadCourseById(courseId);
        dq.setTeacher(teacher);
        dq.setCourse(course);
        dq.setText(text);
        dq.setTitle(title);
        return dq;
    }

    public List<Questions> loadCourseQBForTeacher(long teacherId, long courseId) {
        return teacherRepository.loadCourseQBForTeacher(teacherId,courseId);
    }
}
