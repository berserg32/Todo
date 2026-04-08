package com.example.todo.repository;

import com.example.todo.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}
