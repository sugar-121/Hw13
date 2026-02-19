package ir.Hw13.repository;

import ir.Hw13.entity.Course;
import ir.Hw13.entity.Student;
import ir.Hw13.entity.StudentTakeTestAttempt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentRepositoryImpl implements BaseRepository<Student> {

    private final EntityManager entityManager;

    public StudentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Student student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }


    public boolean logIn(long id, String password) {
        TypedQuery<Long> query = entityManager.createQuery("select count(s) from Student s where id = :id and password = :password", long.class);
        query.setParameter("id", id);
        query.setParameter("password", password);
        return query.getSingleResult() == 1;
    }

    public List<Course> loadStudentCourses(long studentId) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Student s join s.courses c where s.id =: studentId", Course.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    public Student loadStudentById(long studentId) {
        return entityManager.find(Student.class,studentId);
    }


}
