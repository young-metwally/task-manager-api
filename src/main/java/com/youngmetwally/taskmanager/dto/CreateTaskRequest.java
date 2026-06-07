package com.youngmetwally.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be 255 characters or less")
    private String title;

    // Constructors
    public CreateTaskRequest() {}

    public CreateTaskRequest(String title) {
        this.title = title;
    }

    // Getter
    public String getTitle() { return title; }

    // Setter
    public void setTitle(String title) { this.title = title; }
}