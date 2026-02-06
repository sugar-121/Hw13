package ir.Hw13.repository;

import ir.Hw13.entity.Course;
import ir.Hw13.entity.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TeacherRepositoryImpl implements BaseRepository<Teacher> {

    private final EntityManager entityManager;

    public TeacherRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void signUp(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
    }

    public boolean logIn(long id, String password) {
        TypedQuery<Long> query = entityManager.createQuery("select count(t) from Teacher t where id = :id and password = :password", long.class);
        query.setParameter("id", id);
        query.setParameter("password", password);
        return query.getSingleResult() == 1;
    }

    public List<Course> showTeacherCourses(long teacherId) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c where teacher.id = :id", Course.class);
        query.setParameter("id", teacherId);
        return query.getResultList();
    }
}
