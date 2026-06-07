package com.youngmetwally.taskmanager.service;

import com.youngmetwally.taskmanager.dto.CreateTaskRequest;
import com.youngmetwally.taskmanager.dto.TaskResponse;
import com.youngmetwally.taskmanager.exception.TaskNotFoundException;
import com.youngmetwally.taskmanager.model.Task;
import com.youngmetwally.taskmanager.model.TaskStatus;
import com.youngmetwally.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse completeTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(TaskStatus.COMPLETED);
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    @Override
    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }
}