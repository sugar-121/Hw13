package ir.Hw13.repository;

import ir.Hw13.entity.Teacher;
import ir.Hw13.util.ApplicationContext;
import jakarta.persistence.EntityManager;

public class TeacherRepositoryImpl implements BaseRepository<Teacher> {

    private EntityManager entityManager;

    public TeacherRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void signUp(Teacher teacher) {
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
    }
}
