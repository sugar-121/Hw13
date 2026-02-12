package ir.Hw13.repository;

import ir.Hw13.entity.*;
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
        TypedQuery<Long> query = entityManager.createQuery("select count(p) from Manager p where p.id =: id and p.password =: password", long.class);
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

    public Course fetchCourseByTitle(String title) {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c where c.title =: title", Course.class);
        query.setParameter("title", title);
        return query.getSingleResultOrNull();
    }

    public void dropCourse(Course course) {
        entityManager.getTransaction().begin();
        entityManager.remove(course);
        entityManager.getTransaction().commit();
    }

    public Teacher loadTeacherById(long id) {
        return entityManager.find(Teacher.class, id);
    }

    public Student loadStudentById(long id) {
        return entityManager.find(Student.class, id);
    }

//    public void addTeacherToCourse(Teacher teacher){
//        entityManager.getTransaction().begin();
//        entityManager.merge(teacher);
//        entityManager.getTransaction().commit();
//    }
//
//    public void addStudentToCourse(Course course) {
//        entityManager.getTransaction().begin();
//        entityManager.merge(course);
//        entityManager.getTransaction().commit();
//    }

    public void changeToCourse(Course course) {
        entityManager.getTransaction().begin();
        entityManager.merge(course);
        entityManager.getTransaction().commit();
    }

    public List<Long> fetchStudentCourses(long id) {
        TypedQuery<Long> query = entityManager.createQuery("select c.id from Student s join s.courses c where s.id = : id", Long.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Long> fetchTeacherCourses(long id) {
        TypedQuery<Long> query = entityManager.createQuery("select c.id from Course c where c.teacher.id = : id", Long.class);
        query.setParameter("id", id);
        return query.getResultList();

    }

    public Course loadCourseById(long id) {
       return entityManager.find(Course.class,id);
    }
}
