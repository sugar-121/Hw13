package ir.Hw13.repository;

import ir.Hw13.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

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
        return entityManager.find(Tests.class, testId);
    }

    public void takeTest(StudentTakeTestAttempt attempt) {
        entityManager.getTransaction().begin();
        entityManager.persist(attempt);
        entityManager.getTransaction().commit();
    }

    public Questions loadQuestionById(long questionId) {
        return entityManager.find(Questions.class, questionId);
    }

    public Choice loadChoiceById(long choiceId) {
        return entityManager.find(Choice.class, choiceId);
    }

    public void insertAnswerToTest(StudentAnswer answer) {
        entityManager.getTransaction().begin();
        entityManager.persist(answer);
        entityManager.getTransaction().commit();
    }

    public void finishTest(StudentTakeTestAttempt attempt) {
        entityManager.getTransaction().begin();
        entityManager.merge(attempt);
        entityManager.getTransaction().commit();
    }

    public List<StudentTakeTestAttempt> loadTestAttempts(long testId) {
        TypedQuery<StudentTakeTestAttempt> query = entityManager.createQuery("select a from StudentTakeTestAttempt a where a.test.id =: testId", StudentTakeTestAttempt.class);
        query.setParameter("testId", testId);
        return query.getResultList();
    }
}
