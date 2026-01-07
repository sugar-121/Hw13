package ir.Hw13.repository;

import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ManagerRepository {
    private EntityManager entityManager;

    public ManagerRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<Person> loadSignUpRequests(){
        TypedQuery<Person> query = entityManager.createQuery("select p from Person p where p.status = : status", Person.class);
        query.setParameter("status", Status.WAITING_FOR_SUBMIT);
        return query.getResultList();

    }
}
