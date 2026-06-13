package com.heena.taskmanager.service;

import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.model.Task;
import com.heena.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public List<TaskResponseDTO> getTasksByStatus(Status status) {
        List<Task> tasks = taskRepository.findByStatus(status);

        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public List<TaskResponseDTO> getCompletedTasksUsingQuery() {
        List<Task> tasks = taskRepository.findCompletedTasks();

        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public Optional<TaskResponseDTO> getTaskResponseById(Long id) {
        return taskRepository.findById(id).map(this::mapToTaskResponse);
    }

    public Optional<TaskResponseDTO> updateTask(Long id, TaskRequestDTO request) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(request.getTitle());
                    task.setDescription(request.getDescription());
                    task.setPriority(request.getPriority());
                    task.setStatus(request.getStatus());

                    Task savedTask = taskRepository.save(task);

                    return mapToTaskResponse(savedTask);
                });
    }

    public boolean deleteTask(Long id) {

        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public TaskResponseDTO addTask(TaskRequestDTO request) {
        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    private TaskResponseDTO mapToTaskResponse(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}