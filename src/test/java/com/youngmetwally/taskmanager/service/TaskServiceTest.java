package com.youngmetwally.taskmanager.service;

import com.youngmetwally.taskmanager.dto.CreateTaskRequest;
import com.youngmetwally.taskmanager.dto.TaskResponse;
import com.youngmetwally.taskmanager.exception.TaskNotFoundException;
import com.youngmetwally.taskmanager.model.Task;
import com.youngmetwally.taskmanager.model.TaskStatus;
import com.youngmetwally.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId("1");
        task.setTitle("Buy groceries");
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should create task and return response")
    void shouldCreateTask() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest("Buy groceries");
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        TaskResponse result = taskService.createTask(request);

        // Assert
        assertNotNull(result);
        assertEquals("Buy groceries", result.getTitle());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should return all tasks")
    void shouldGetAllTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(List.of(task));

        // Act
        List<TaskResponse> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Buy groceries", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Should complete task and return COMPLETED status")
    void shouldCompleteTask() {
        // Arrange
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        task.setStatus(TaskStatus.COMPLETED);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        TaskResponse result = taskService.completeTask("1");

        // Assert
        assertEquals(TaskStatus.COMPLETED, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw TaskNotFoundException when completing missing task")
    void shouldThrowWhenCompletingMissingTask() {
        // Arrange
        when(taskRepository.findById("bad-id")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class,
                () -> taskService.completeTask("bad-id"));
    }

    @Test
    @DisplayName("Should delete task successfully")
    void shouldDeleteTask() {
        // Arrange
        when(taskRepository.existsById("1")).thenReturn(true);

        // Act
        taskService.deleteTask("1");

        // Assert
        verify(taskRepository, times(1)).deleteById("1");
    }

    @Test
    @DisplayName("Should throw TaskNotFoundException when deleting missing task")
    void shouldThrowWhenDeletingMissingTask() {
        // Arrange
        when(taskRepository.existsById("bad-id")).thenReturn(false);

        // Act & Assert
        assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask("bad-id"));
    }
}