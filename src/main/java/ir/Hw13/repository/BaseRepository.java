package ir.Hw13.repository;

import ir.Hw13.entity.Person;
import jakarta.persistence.EntityManager;

public interface BaseRepository<T> {
    void signUp(T t);

}
