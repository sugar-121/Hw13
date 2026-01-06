package ir.Hw13.repository;

import ir.Hw13.entity.Person;
import jakarta.persistence.EntityManager;

public class BaseRepository<T extends Person> {
    private EntityManager entityManager;


      public BaseRepository(EntityManager entityManager){
          this.entityManager = entityManager;
      }

      public void signUp(T t){
          entityManager.getTransaction().begin();
          entityManager.persist(t);
          entityManager.getTransaction().commit();
      }
}
