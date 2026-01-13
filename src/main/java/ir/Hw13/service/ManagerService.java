package ir.Hw13.service;

import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.dto.mapper.PersonMapper;
import ir.Hw13.dto.mapper.StudentMapper;
import ir.Hw13.dto.mapper.TeacherMapper;
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

import java.util.List;
import java.util.Objects;

public class ManagerService {
    private final EntityManager entityManager = ApplicationContext.getInstance().getEntityManager();
    private final ManagerRepository managerRepository = ApplicationContext.getInstance().getManagerRepository();
    private final PersonMapper personMapper = ApplicationContext.getInstance().getPersonMapper();
    private final TeacherMapper teacherMapper = ApplicationContext.getInstance().getTeacherMapper();
    private final StudentMapper studentMapper = ApplicationContext.getInstance().getStudentMapper();
    private final StudentRepositoryImpl studentRepository = ApplicationContext.getInstance().getStudentRepository();
    private final TeacherRepositoryImpl teacherRepository = ApplicationContext.getInstance().getTeacherRepository();

    public boolean logIn(long id, String password) {
        TypedQuery<Long> query = entityManager.createQuery("select count(p) from Person p where p.id =: id and p.password =: password", Long.class);
        query.setParameter("id", id);
        query.setParameter("password", password);
        if (query.getSingleResult() == 1) {
            return true;
        } else {
            return false;
        }
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
}
