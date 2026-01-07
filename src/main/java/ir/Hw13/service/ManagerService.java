package ir.Hw13.service;

import ir.Hw13.dto.mapper.PersonMapper;
import ir.Hw13.entity.Person;
import ir.Hw13.repository.ManagerRepository;
import ir.Hw13.util.ApplicationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ManagerService {
    EntityManager entityManager = ApplicationContext.getInstance().getEntityManager();
    ManagerRepository managerRepository = ApplicationContext.getInstance().getManagerRepository();
    PersonMapper personMapper = ApplicationContext.getInstance().getPersonMapper();

    public boolean logIn(long id, String password) {
        TypedQuery<Long> query = entityManager.createQuery("select count(p) from Person p where p.id =: id and p.password =: password", Long.class);
        query.setParameter("id", id);
        query.setParameter("password", password);
        if (query.getSingleResultOrNull() != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> loadSignUpRequests() {
        return personMapper.personLoader(
                managerRepository.loadSignUpRequests());

    }
}
