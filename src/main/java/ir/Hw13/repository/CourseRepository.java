package ir.Hw13.repository;

import ir.Hw13.entity.Course;
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

}
