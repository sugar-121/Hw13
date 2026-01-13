package ir.Hw13.repository;

import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;

public class ManagerRepository {
    private final EntityManager entityManager;

    public ManagerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Person> loadSignUpRequests() {
        TypedQuery<Person> query = entityManager.createQuery("select p from Person p where p.status = : status", Person.class);
        query.setParameter("status", Status.WAITING_FOR_SUBMIT);
        return query.getResultList();
    }

    public void submitAll(List<Person> people) {
        entityManager.getTransaction().begin();
        people.forEach(person -> entityManager.merge(person));
        entityManager.getTransaction().commit();
    }

    public void submitOne(Person person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

    public Person loadById(long id) {
        return entityManager.find(Person.class, id);
    }

    public void deleteUser(Person person) {
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }

    public void update(Person person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

    public List<Person> applyFilter(String filteredType,
                                    String filteredFirstName,
                                    String filteredLastName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cbQuery = cb.createQuery(Person.class);
        Root<Person> root = cbQuery.from(Person.class);
        if (!Objects.isNull(filteredType)) {
            cbQuery.where(cb.equal(root.type(), filteredType));
        }
        if (!Objects.isNull(filteredFirstName)) {
            cbQuery.where(cb.like(root.get("firstName"), filteredFirstName + "%"));
        }
        if (!Objects.isNull(filteredLastName)) {
            cbQuery.where(cb.like(root.get("lastName"), filteredLastName + "%"));
        }
        return entityManager.createQuery(cbQuery).getResultList();
    }
}
