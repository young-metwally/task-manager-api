package com.youngmetwally.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngmetwally.taskmanager.dto.CreateTaskRequest;
import com.youngmetwally.taskmanager.dto.TaskResponse;
import com.youngmetwally.taskmanager.exception.TaskNotFoundException;
import com.youngmetwally.taskmanager.model.TaskStatus;
import com.youngmetwally.taskmanager.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/v1/tasks returns 201 and task")
    void shouldCreateTask() throws Exception {
        TaskResponse response = new TaskResponse(
                "1", "Buy groceries", TaskStatus.PENDING, LocalDateTime.now());

        when(taskService.createTask(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateTaskRequest("Buy groceries"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Buy groceries"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("POST /api/v1/tasks with blank title returns 400")
    void shouldReturn400WhenTitleBlank() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CreateTaskRequest(""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/tasks returns 200 and list")
    void shouldGetAllTasks() throws Exception {
        TaskResponse response = new TaskResponse(
                "1", "Buy groceries", TaskStatus.PENDING, LocalDateTime.now());

        when(taskService.getAllTasks()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Buy groceries"));
    }

    @Test
    @DisplayName("PUT /api/v1/tasks/{id}/complete returns 200")
    void shouldCompleteTask() throws Exception {
        TaskResponse response = new TaskResponse(
                "1", "Buy groceries", TaskStatus.COMPLETED, LocalDateTime.now());

        when(taskService.completeTask("1")).thenReturn(response);

        mockMvc.perform(put("/api/v1/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    @DisplayName("DELETE /api/v1/tasks/{id} returns 204")
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET non-existent task returns 404")
    void shouldReturn404WhenTaskNotFound() throws Exception {
        when(taskService.completeTask("bad-id"))
                .thenThrow(new TaskNotFoundException("bad-id"));

        mockMvc.perform(put("/api/v1/tasks/bad-id/complete"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("TASK_NOT_FOUND"));
    }
}