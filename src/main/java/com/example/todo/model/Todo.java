package com.example.todo.model;

public class Todo {
    private final Long id;
    private String title, description, status;

    public Todo(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Todo{");
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }


}
