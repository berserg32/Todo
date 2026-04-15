package com.example.todo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.todo.model.Status;
import com.example.todo.model.Todo;
import com.example.todo.model.TodoRequest;
import com.example.todo.repository.TodoRepository;


@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository){
        this.repository = repository;
    }

    private Todo findById(Long id){
        return repository.findById(id)
            .orElseThrow(NoSuchElementException::new);
    }

    public Todo create(TodoRequest q){
        return repository.save(
            new Todo(
                q.getTitle(),
                q.getDescription(),
                q.getStatus()
            )
        );
    }

    @Cacheable(value = "todos", key = "#id")
    public Todo get(Long id){
        return findById(id);
    }
    @CacheEvict(value = "todos", key = "#id")
    public Todo delete(Long id){
        Todo temp = findById(id);
        repository.delete(temp);
        return temp;
    }
    @CacheEvict(value = "todos", key = "#id")
    public Todo put(Long id, TodoRequest q){
        Todo temp = findById(id);
        temp.setDescription(q.getDescription());
        temp.setStatus(q.getStatus());
        temp.setTitle(q.getTitle());
        return repository.save(temp);
    }

    public List<Todo> getFilter(Status status){
        if(status == null) return repository.findAll();
        return repository.findByStatus(status);
    }
}
