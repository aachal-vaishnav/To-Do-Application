package com.example.ToDo.service;

import com.example.ToDo.entity.ToDo;
import com.example.ToDo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void updateTodo(Long id, ToDo newTodo){
        //possibility that we can get null value from database so using Optional<> in repository
        Optional<ToDo> todoOldBox = todoRepository.findTodoById(id);//old to-do
        if(todoOldBox.isPresent()){
            ToDo oldTodo = todoOldBox.get();
            oldTodo.setToDoContent(newTodo.getToDoContent());
            oldTodo.setComplete(newTodo.isComplete());
            todoRepository.updateTodo(oldTodo);
        }
    }
}
