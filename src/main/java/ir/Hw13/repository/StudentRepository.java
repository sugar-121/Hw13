package ir.Hw13.repository;

import jakarta.persistence.EntityManager;

public class StudentRepository {
     private EntityManager entityManager;

     public StudentRepository(EntityManager entityManager){
         this.entityManager = entityManager;
     }
}
