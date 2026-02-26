package ir.Hw13.repository;

import ir.Hw13.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Objects;

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
        if (answer.getId() == null) {
            entityManager.persist(answer);
        } else {
            entityManager.merge(answer);
        }
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

    public List<StudentAnswer> loadStudentAnswers(StudentTakeTestAttempt attempt) {
        TypedQuery<StudentAnswer> query = entityManager.createQuery("select a from StudentAnswer a where a.attempt.id =: attemptId", StudentAnswer.class);
        query.setParameter("attemptId", attempt.getId());
        return query.getResultList();

    }

    public Long getQuestionScoreInTest(long testId, long questionId) {
        TypedQuery<Long> query = entityManager.createQuery("select t.score from TestQuestion t where t.tests.id =: testId and t.questions.id =: questionId", Long.class);
        query.setParameter("testId", testId);
        query.setParameter("questionId", questionId);
        return query.getSingleResultOrNull();
    }


    public void autoGradeQuestion(StudentAnswer answer) {
        entityManager.getTransaction().begin();
        entityManager.merge(answer);
        entityManager.getTransaction().commit();
    }


}
