//package ir.Hw13.repository;
//
//import ir.Hw13.entity.Person;
//import ir.Hw13.util.ApplicationContext;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//
//public class BaseRepositoryImpl<T extends Person> implements BaseRepository<T>{
//
//    private final EntityManager entityManager = ApplicationContext.getInstance().getEntityManager();
//    @Override
//    public void signUp(T t) {
//        entityManager.getTransaction().begin();
//        entityManager.persist(t);
//        entityManager.getTransaction().commit();
//    }
//
//    @Override
//    public boolean logIn(long id, String password) {
//        TypedQuery<Long> query = entityManager.createQuery("select count(p) from Person p where p.id =: id and p.password =: password", Long.class);
//        query.setParameter("id", id);
//        query.setParameter("password", password);
//        return query.getSingleResult() == 1;
//    }
//}
