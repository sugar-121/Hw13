package ir.Hw13.ui;

import ir.Hw13.entity.Manager;
import ir.Hw13.entity.Status;
import ir.Hw13.util.ApplicationContext;
import jakarta.persistence.EntityManager;

import javax.swing.text.html.parser.Entity;

public class Main {
    public static void main(String[] args) {

        /*Manager manager = new Manager("admin",
                "admin",
                "121",
                Status.SUBMITTED);
        EntityManager entityManager = ApplicationContext.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(manager);
        entityManager.getTransaction().commit();*/
        //manager: id=1   password=121

        Menu menu = new Menu();
        menu.start();
    }
}
