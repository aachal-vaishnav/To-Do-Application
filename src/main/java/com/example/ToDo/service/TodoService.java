package com.example.ToDo.service;

import com.example.ToDo.entity.ToDo;
import com.example.ToDo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public void saveTodo(ToDo todo){
        todoRepository.save(todo);
    }
    public List<ToDo> getAllTodos(){
        return todoRepository.findAll();
    }
}
