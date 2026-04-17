package com.example.todo.model;

public class TodoRequest {
    private String title;
    private String description;
    private Status status;
    public TodoRequest(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TodoRequest(){}

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
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
