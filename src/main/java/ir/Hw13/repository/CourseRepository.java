package ir.Hw13.repository;

import ir.Hw13.entity.Course;
import ir.Hw13.entity.Tests;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CourseRepository {

    private final EntityManager entityManager;

    public CourseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Course fetchCourseById(long courseId) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c where c.id = :id", Course.class);
        query.setParameter("id" , courseId);
        return query.getSingleResultOrNull();
    }

    public List<Tests> loadCourseTests(long courseId){
        TypedQuery<Tests> query = entityManager.createQuery("select t from Course c join c.tests t where c.id =: courseId", Tests.class);
        query.setParameter("courseId" , courseId);
        return query.getResultList();
    }


    public List<Tests> loadCourseTestsForStudent(long studentId, long courseId) {
        TypedQuery<Tests> query = entityManager.createQuery("select t from Course c join c.tests t join c.students s where c.id =: courseId and s.id =: studentId", Tests.class);
        query.setParameter("courseId" , courseId);
        query.setParameter("studentId" , studentId);
        return query.getResultList();
    }
}
