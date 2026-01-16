package ir.Hw13.repository;

import ir.Hw13.entity.Course;
import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManagerRepository {
    private final EntityManager entityManager;

    public ManagerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean logIn(long id, String password) {
        TypedQuery<Long> query = entityManager.createQuery("select count(p) from Person p where p.id =: id and p.password =: password", Long.class);
        query.setParameter("id", id);
        query.setParameter("password", password);
        return query.getSingleResult() == 1;
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
        List<Predicate> conditions = new ArrayList<>();
        if (!Objects.isNull(filteredType)) {
            conditions.add(cb.equal(root.type(), filteredType));
        }
        if (!Objects.isNull(filteredFirstName)) {
            conditions.add(cb.like(root.get("firstName"), filteredFirstName + "%"));
        }
        if (!Objects.isNull(filteredLastName)) {
            conditions.add(cb.like(root.get("lastName"), filteredLastName + "%"));
        }

        cbQuery.where(cb.and(conditions));
        return entityManager.createQuery(cbQuery).getResultList();
    }

    public void addCourse(Course course) {
        entityManager.getTransaction().begin();
        entityManager.persist(course);
        entityManager.getTransaction().commit();
    }
}
