package com.heena.taskmanager.service;

import com.heena.taskmanager.dto.PageResponseDTO;
import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.model.Task;
import com.heena.taskmanager.repository.TaskRepository;

import com.heena.taskmanager.repository.specification.TaskSpecification;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponseDTO> getTasksByStatus(Status status) {
        List<Task> tasks = taskRepository.findByStatus(status);

        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public PageResponseDTO<TaskResponseDTO> getAllFilteredTasks(
            @Nullable String title, @Nullable Status status, @Nullable Priority priority,
            @Nullable Category category,Pageable pageable) {

        Specification<Task> spec = Specification.allOf();

        if (title != null && !title.isBlank()) {
            spec = spec.and(TaskSpecification.hasTitle(title));
        }
        if (status != null) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }
        if (priority != null) {
            spec = spec.and(TaskSpecification.hasPriority(priority));
        }
        if (category != null) {
            spec = spec.and(TaskSpecification.hasCategory(category));
        }

        Page<Task> taskPages = taskRepository.findAll(spec, pageable);
        return mapToPageTaskResponse(taskPages);
    }

    public List<TaskResponseDTO> getCompletedTasksUsingQuery() {
        List<Task> tasks = taskRepository.findCompletedTasks();

        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public List<TaskResponseDTO> findOverdueTasksUsingQuery() {
        List<Task> tasks = taskRepository.findOverdueTasks();

        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public Optional<TaskResponseDTO> getTaskResponseById(Long id) {
        return taskRepository.findById(id).map(this::mapToTaskResponse);
    }

    public Optional<TaskResponseDTO> updateTask(Long id, TaskRequestDTO request) {
        return taskRepository.findById(id)
                .map(task -> mapToTaskResponse(mapFromTaskRequest(task, request)));
    }

    public Optional<TaskResponseDTO> markTaskAsCompleted(Long id) {
        return taskRepository.findById(id).map(task -> {
            if(task.getStatus() != Status.DONE) {
                task.setStatus(Status.DONE);
                task.setUpdatedAt(LocalDateTime.now());

                taskRepository.save(task);
            }
            return mapToTaskResponse(task);
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
        Task savedTask = mapFromTaskRequest(task, request);

        return mapToTaskResponse(savedTask);
    }

    private TaskResponseDTO mapToTaskResponse(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCategory(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate()
        );
    }

    private Task mapFromTaskRequest(Task task, TaskRequestDTO request) {
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCategory(request.getCategory());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        return taskRepository.save(task);
    }

    private PageResponseDTO<TaskResponseDTO> mapToPageTaskResponse(Page<Task> taskPages) {
        List<TaskResponseDTO> tasks = taskPages.map(this::mapToTaskResponse).toList();
        return new PageResponseDTO<>(
                tasks,
                taskPages.getNumber(),
                taskPages.getTotalPages(),
                taskPages.getTotalElements()
        );
    }
}