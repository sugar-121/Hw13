package ir.Hw13.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Objects;

public class ApplicationContext {
    private static ApplicationContext context;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private ApplicationContext(){}

    public static ApplicationContext getInstance(){
        if (Objects.isNull(context)){
            context = new ApplicationContext();
        }
        return context;
    }

    private EntityManagerFactory getEntityManagerFactory(){
        if (Objects.isNull(entityManagerFactory)){
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        return entityManagerFactory;
    }

    public EntityManager getEntityManager(){
        if (Objects.isNull(entityManager)){
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

}
