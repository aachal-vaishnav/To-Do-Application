package com.example.ToDo.repository;

import com.example.ToDo.entity.ToDo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {
    //EntityManager interface methods

    @PersistenceContext
    private EntityManager entityManager;

    public List<ToDo> findAll(){ //Hibernate Query language
        TypedQuery<ToDo> typedQuery = entityManager.createQuery("from ToDo",ToDo.class);
        return typedQuery.getResultList();
    }

    public Optional<ToDo> findTodoById(Long id){
        ToDo todo = entityManager.find(ToDo.class,id);
        return Optional.ofNullable(todo);//possibility of returning null value so use Optiona;.ofNullable
    }

    // @Transactional runs this method inside a database transaction
    @Transactional //because we are doing transactions when we're modifying the records we add this annotation
    public void save(ToDo todo){
        entityManager.persist(todo);
    }

    @Transactional // @Transactional ensures database changes are committed on success and rolled back on failure

    public void updateTodo(ToDo todo){
        entityManager.merge(todo);
    }
}
