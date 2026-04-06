package com.example.todo.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;

import com.example.todo.model.Todo;


@RestController
public class TodoController {
    private List<Todo> todos = new ArrayList<>();
    
    @PostMapping("/todos")
    public String todoCreate(){
        todos.add(new Todo());
        return "Done!";
    }

}
