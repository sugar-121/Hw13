package ir.Hw13.repository;

import ir.Hw13.entity.StudentTakeTestAttempt;
import ir.Hw13.entity.Tests;
import jakarta.persistence.EntityManager;

public class TestRepository {

    private EntityManager entityManager;

    public TestRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void AddTest(Tests test) {
        entityManager.getTransaction().begin();
        entityManager.persist(test);
        entityManager.getTransaction().commit();
    }

    public Tests loadTestById(long testId) {
        return entityManager.find(Tests.class,testId);
    }

    public void takeTest(StudentTakeTestAttempt attempt) {
        entityManager.getTransaction().begin();
        entityManager.persist(attempt);
        entityManager.getTransaction().commit();
    }
}
