package ir.Hw13.repository;

import ir.Hw13.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TeacherRepositoryImpl implements BaseRepository<Teacher> {

    private final EntityManager entityManager;

    public TeacherRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Teacher teacher) {
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

    public List<Tests> fetchTeacherCourseTests(long teacherId, long courseId) {
        TypedQuery<Tests> query = entityManager.createQuery("select t from Tests t where teacher.id = : teacherId and course.id = : courseId", Tests.class);
        query.setParameter("teacherId", teacherId);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }


    public void AddTest(Tests test) {
        entityManager.getTransaction().begin();
        entityManager.persist(test);
        entityManager.getTransaction().commit();
    }

    public void addMCQ(MultipleChoiceQuestion mcq) {
        entityManager.getTransaction().begin();
        entityManager.persist(mcq);
        entityManager.getTransaction().commit();
    }

    public void makeChoice(Choice choice) {
        entityManager.getTransaction().begin();
        entityManager.persist(choice);
        entityManager.getTransaction().commit();
    }

    public Tests loadTestById(long id) {
        return entityManager.find(Tests.class, id);
    }

    public void addQToTest(TestQuestion testQuestion) {
        entityManager.getTransaction().begin();
        entityManager.persist(testQuestion);
        entityManager.getTransaction().commit();
    }

    public List<Questions> loadCourseQBForTeacher(long teacherId, long courseId) {
        TypedQuery<Questions> query = entityManager.createQuery("select q from Questions q where q.teacher.id = :teacherId and q.course.id = :courseId", Questions.class);
        query.setParameter("teacherId", teacherId);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    public Tests loadTeacherTest(long teacherId, long testId) {
        TypedQuery<Tests> query = entityManager.createQuery("select t from Tests t where t.id = : testId and t.teacher.id = :teacherId", Tests.class);
        query.setParameter("teacherId", teacherId);
        query.setParameter("testId", testId);
        return query.getSingleResultOrNull();
    }
}
