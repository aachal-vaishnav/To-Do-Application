package com.example.ToDo.controller;

import com.example.ToDo.entity.ToDo;
import com.example.ToDo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoController {
    @Autowired
    TodoService todoService;


    //return task while hit localhost:8080
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String home() {
//        return "task";
//    }

    @RequestMapping("/")
    public String getAllToDo(Model model){
        //fetch record
        List<ToDo> listOfTodos= todoService.getAllTodos();
        model.addAttribute("todoList",listOfTodos);
        return "task";
    }

    @RequestMapping(value = "/addtodo",method = RequestMethod.POST)
    @ResponseBody //returning view name but here it returns success as string in browser for now
    public String createToDo(@ModelAttribute ToDo todo){
        //want here to do object with the task content
        todoService.saveTodo(todo);
        return "success";
    }
    @RequestMapping(value= "/updatetodo/{id}")
    public String updateToDo(@PathVariable("id") Long id,@ModelAttribute ToDo todo){
        todoService.updateTodo(id,todo);
        return "redirect:/";
    }
    @RequestMapping("/deleteToDo/{id}")
    public String deleteToDo(@PathVariable("id") Long id){
        todoService.deleteTodo(id);
        return "redirect:/";
    }
}
