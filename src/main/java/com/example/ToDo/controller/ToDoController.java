package com.example.ToDo.controller;

import com.example.ToDo.entity.ToDo;
import com.example.ToDo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ToDoController {
    @Autowired
    TodoService todoService;


    //return task while hit localhost:8080
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "task";
    }

    @RequestMapping(value = "/addtodo",method = RequestMethod.POST)
    @ResponseBody //returning view name but here it returns success as string in browser for now
    public String createToDo(@ModelAttribute ToDo todo){
        //want here to do object with the task content
        todoService.saveTodo(todo);
        return "success";
    }
//    public String updateToDo(){
//
//    }
//    public String deleteToDo(){
//
//    }
//    public String getAllToDo(){
//
//    }
}
