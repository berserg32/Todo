package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoRequest;

@Service
public class TodoService {
    private final List<Todo> todos = new ArrayList<>();
    private Long idCounter = 0L;

    private Todo findById(Long id){
        return todos
            .stream()
            .filter(prdct -> prdct.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Todo create(TodoRequest q){
        todos.add(new Todo(++idCounter,
            q.getTitle(),
            q.getDescription(),
            q.getStatus()));
        return todos.get(todos.size()-1);
    }

    public Todo get(Long id){
        return findById(id);
    }

    public Todo delete(Long id){
        Todo temp = findById(id);
        todos.remove(temp);
        return temp;
    }

    public Todo put(Long id, TodoRequest q){
        Todo temp = findById(id);
        temp.setDescription(q.getDescription());
        temp.setStatus(q.getStatus());
        temp.setTitle(q.getTitle());
        return temp;
    }

    public List<Todo> getFilter(String status){
        if(status == null) return List.copyOf(todos);
        return todos.stream()
            .filter(prdct -> status.equals(prdct.getStatus()))
            .toList();
    }
}
