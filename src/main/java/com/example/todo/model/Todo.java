package com.example.todo.model;

public class Todo {
    private Long count = 0L;
    private Long id;
    private String title, description, status;

    private void setId(){
        id = ++count;
    }

    public Todo(String title, String description, String status) {
        setId();
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Todo(){
        setId();
        title = "";
        description = "";
        status = "";
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
