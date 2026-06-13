package com.heena.taskmanager.service;

import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.model.Task;
import com.heena.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        for (Task task : tasks) {
            taskResponseDTOS.add(mapToTaskResponse(task));
        }
        return taskResponseDTOS;
    }

    public List<TaskResponseDTO> getTasksByStatus(Status status) {
        List<Task> tasks = taskRepository.findByStatus(status);

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        for (Task task : tasks) {
            taskResponseDTOS.add(mapToTaskResponse(task));
        }
        return taskResponseDTOS;
    }

    public List<TaskResponseDTO> getCompletedTasksUsingQuery() {
        List<Task> tasks = taskRepository.findCompletedTasks();

        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        for (Task task : tasks) {
            taskResponseDTOS.add(mapToTaskResponse(task));
        }
        return taskResponseDTOS;
    }

    public Optional<TaskResponseDTO> getTaskResponseById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            return Optional.empty();
        }

        Task savedTask = task.get();

        return Optional.of(mapToTaskResponse(savedTask));
    }

    public Optional<TaskResponseDTO> updateTask(Long id, TaskRequestDTO request) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {
            Task task = existingTask.get();

            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setPriority(request.getPriority());
            task.setStatus(request.getStatus());
            task.setUpdatedAt(LocalDateTime.now());

            Task savedTask = taskRepository.save(task);
            return Optional.of(mapToTaskResponse(savedTask));
        }

        return Optional.empty();
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
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

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