package com.youngmetwally.taskmanager.service;

import com.youngmetwally.taskmanager.dto.CreateTaskRequest;
import com.youngmetwally.taskmanager.dto.TaskResponse;
import java.util.List;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    List<TaskResponse> getAllTasks();

    TaskResponse completeTask(String id);

    void deleteTask(String id);
}