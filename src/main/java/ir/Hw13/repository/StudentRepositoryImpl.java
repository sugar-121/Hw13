package ir.Hw13.repository;

import ir.Hw13.entity.Student;
import jakarta.persistence.EntityManager;

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


}
