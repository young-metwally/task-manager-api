package com.youngmetwally.taskmanager.dto;

import com.youngmetwally.taskmanager.model.TaskStatus;
import java.time.LocalDateTime;

public class TaskResponse {

    private String id;
    private String title;
    private TaskStatus status;
    private LocalDateTime createdAt;

    // Constructors
    public TaskResponse() {}

    public TaskResponse(String id, String title, TaskStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}