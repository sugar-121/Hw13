package ir.Hw13.service;

import ir.Hw13.dto.CourseDto;
import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.dto.mapper.CourseMapper;
import ir.Hw13.dto.mapper.PersonMapper;
import ir.Hw13.dto.mapper.StudentMapper;
import ir.Hw13.dto.mapper.TeacherMapper;
import ir.Hw13.entity.Course;
import ir.Hw13.entity.Person;
import ir.Hw13.entity.Student;
import ir.Hw13.entity.Teacher;
import ir.Hw13.repository.ManagerRepository;
import ir.Hw13.repository.StudentRepositoryImpl;
import ir.Hw13.repository.TeacherRepositoryImpl;
import ir.Hw13.util.ApplicationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ManagerService {
    private final EntityManager entityManager = ApplicationContext.getInstance().getEntityManager();
    private final ManagerRepository managerRepository = ApplicationContext.getInstance().getManagerRepository();
    private final PersonMapper personMapper = ApplicationContext.getInstance().getPersonMapper();
    private final TeacherMapper teacherMapper = ApplicationContext.getInstance().getTeacherMapper();
    private final StudentMapper studentMapper = ApplicationContext.getInstance().getStudentMapper();
    private final StudentRepositoryImpl studentRepository = ApplicationContext.getInstance().getStudentRepository();
    private final TeacherRepositoryImpl teacherRepository = ApplicationContext.getInstance().getTeacherRepository();
    private final Validator validator = ApplicationContext.getInstance().getValidator();

    public boolean logIn(long id, String password) {
        return managerRepository.logIn(id, password);
    }

    public List<String> loadSignUpRequests() {
        return personMapper.personLoader(
                managerRepository.loadSignUpRequests());
    }

    public void submitAll() {
        List<Person> people = managerRepository.loadSignUpRequests();
        List<Person> submitted = personMapper.submitAll(people);
        managerRepository.submitAll(submitted);
    }

    public void submitOne(long id) {
        Person person = entityManager.find(Person.class, id);
        Person submitted = personMapper.submitOne(person);
        managerRepository.submitOne(submitted);

    }

    public Person loadById(long id) {
        return managerRepository.loadById(id);
    }

    public void updateUser(PersonUpdateDto dto, Person fetchedPerson) {
        if (Objects.isNull(dto.getRoll())) {
            Person mapped = personMapper.mapToEntityForUpdate(dto, fetchedPerson);
            managerRepository.update(mapped);

        } else if (dto.getRoll().equals("Student")) {
            Student mapped = teacherMapper.mapTeacherToStudent(dto, fetchedPerson);
            deleteUser(fetchedPerson);
            studentRepository.signUp(mapped);

        } else {
            Teacher mapped = studentMapper.mapStudentToTeacher(dto, fetchedPerson);
            deleteUser(fetchedPerson);
            teacherRepository.signUp(mapped);
        }
    }

    public void deleteUser(Person person) {
        managerRepository.deleteUser(person);
    }

    public List<Person> applyFilter(String filteredType,
                                    String filteredFirstName,
                                    String filteredLastName) {
        return managerRepository.applyFilter(filteredType, filteredFirstName, filteredLastName);

    }

    public boolean addCourse(CourseDto dto) {
        Set<ConstraintViolation<CourseDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(v.getMessage()));
            return false;
        }
        CourseMapper courseMapper = new CourseMapper();
        managerRepository.addCourse(courseMapper.toEntity(dto));
        return true;
    }

//    public Course fetchCourseByTitle(String title){
//        return managerRepository.fetchCourseByTitle(title);
//    }

    public boolean dropCourse(String title){
        Course fetchedCourse = managerRepository.fetchCourseByTitle(title);
        if(!Objects.isNull(fetchedCourse)){
            managerRepository.dropCourse(fetchedCourse);
            return true;
        }else {
            return false;
        }
    }

    public void addTeacherToCourse(String title, long id) {
        Teacher teacher = managerRepository.loadTeacherById(id);
        Course course = managerRepository.fetchCourseByTitle(title);
        if (!Objects.isNull(course)){
            teacher.getCourses().add(course);
            course.setTeacher(teacher);
            managerRepository.addTeacherToCourse(teacher);
        }
    }
}
