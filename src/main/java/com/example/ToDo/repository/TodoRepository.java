package com.example.ToDo.repository;

import com.example.ToDo.entity.ToDo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {
    //EntityManaeger interface methods

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional //because we are doing transactions when we're modifying the records we add this annotation

    public void save(ToDo todo){
        entityManager.persist(todo);
    }
}
