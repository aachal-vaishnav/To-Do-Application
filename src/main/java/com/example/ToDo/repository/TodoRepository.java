package com.example.ToDo.repository;

import com.example.ToDo.entity.ToDo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {
    //EntityManager interface methods

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional //because we are doing transactions when we're modifying the records we add this annotation

    public void save(ToDo todo){
        entityManager.persist(todo);
    }
    public List<ToDo> findAll(){ //Hibernate Query language
        TypedQuery<ToDo> typedQuery = entityManager.createQuery("from ToDo",ToDo.class);
        return typedQuery.getResultList();
    }
}
