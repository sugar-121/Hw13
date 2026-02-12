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
import ir.Hw13.service.exceptions.CourseNotFoundException;
import ir.Hw13.service.exceptions.NotAStudentException;
import ir.Hw13.service.exceptions.NotATeacherException;
import ir.Hw13.service.exceptions.PersonNotFoundException;
import ir.Hw13.util.ApplicationContext;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ManagerService {
    private final ApplicationContext context = ApplicationContext.getInstance();
    private final EntityManager entityManager = context.getEntityManager();
    private final ManagerRepository managerRepository = context.getManagerRepository();
    private final PersonMapper personMapper = context.getPersonMapper();
    private final TeacherMapper teacherMapper = context.getTeacherMapper();
    private final StudentMapper studentMapper = ApplicationContext.getInstance().getStudentMapper();
    private final StudentRepositoryImpl studentRepository = context.getStudentRepository();
    private final TeacherRepositoryImpl teacherRepository = context.getTeacherRepository();
    private final Validator validator = context.getValidator();
    private final CourseMapper courseMapper = context.getCourseMapper();

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
        Person person = managerRepository.loadById(id);
        Person submitted = personMapper.submitOne(person);
        managerRepository.submitOne(submitted);

    }

    public Person loadPersonById(long id) {
        return managerRepository.loadById(id);
    }

    public Course loadCourseById(long id){
        return managerRepository.loadCourseById(id);
    }

    public void updateUser(PersonUpdateDto dto, Person fetchedPerson) {
        if (Objects.isNull(dto.getRoll())) {
            Person mapped = personMapper.mapToEntityForUpdate(dto, fetchedPerson);
            managerRepository.update(mapped);

        } else if (dto.getRoll().equals("Student")) {

            Student mapped = teacherMapper.mapTeacherToStudent(dto, fetchedPerson);
            deleteUser(fetchedPerson);
            studentRepository.save(mapped);

        } else {
            Teacher mapped = studentMapper.mapStudentToTeacher(dto, fetchedPerson);
            deleteUser(fetchedPerson);
            teacherRepository.save(mapped);
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



    public boolean dropCourse(String title) {
        Course fetchedCourse = managerRepository.fetchCourseByTitle(title);
        if (!Objects.isNull(fetchedCourse)) {
            managerRepository.dropCourse(fetchedCourse);
            return true;
        } else {
            return false;
        }
    }

    public int addTeacherToCourse(String title, long id) {
        Person person = managerRepository.loadById(id);
        Course course = managerRepository.fetchCourseByTitle(title);
        if (person != null) {
            if (person instanceof Teacher teacher) {
                if (!Objects.isNull(course)) {
                    course.setTeacher(teacher);
                    managerRepository.changeToCourse(course);
                } else {
                    throw new CourseNotFoundException(title);
                }
            } else {
                throw new NotATeacherException(id);
            }
        } else {
            throw new PersonNotFoundException(id);
        }
        return 1;
    }

    public void show() {
        Person teacher = managerRepository.loadById(4);
        Set<Course> courses = ((Teacher) teacher).getCourses();
        for (Course course : courses) {
            System.out.println(course.getTeacher().getFirstName());
        }

    }

    public int addStudentToCourse(String title, long id) {
        Person person = managerRepository.loadById(id);
        Course course = managerRepository.fetchCourseByTitle(title);
        if (person != null) {
            if (person instanceof Student student) {
                if (!Objects.isNull(course)) {
                    course.getStudents().add(student);
                    managerRepository.changeToCourse(course);
                } else {
                    throw new CourseNotFoundException(title);
                }
            } else {
                throw new NotAStudentException(id);
            }
        } else {
            throw new PersonNotFoundException(id);
        }
        return 1;
    }

    public void loadCourseByTitle(String courseTitle) {
        Course fetchedCourse = managerRepository.fetchCourseByTitle(courseTitle);
        courseMapper.loadCourse(fetchedCourse);
    }

    public void removeTeacherFromCourse(String title) {
        Course course = managerRepository.fetchCourseByTitle(title);
        course.setTeacher(null);
        managerRepository.changeToCourse(course);
    }

    public void removeStudentFromCourse(int studentId, String courseTitle) {
        Course course = managerRepository.fetchCourseByTitle(courseTitle);
        Student student = managerRepository.loadStudentById(studentId);
        course.getStudents().remove(student);
        managerRepository.changeToCourse(course);
    }

    public boolean isStudentInvolvedInCourses(long id) {
        List<Long> courseIds = managerRepository.fetchStudentCourses(id);
        return !courseIds.isEmpty();
    }

    public boolean isTeacherInvolvedInCourses(long id) {
        List<Long> courseIds = managerRepository.fetchTeacherCourses(id);
        return !courseIds.isEmpty();
    }
}

