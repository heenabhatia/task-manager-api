package com.heena.taskmanager.controller;

import com.heena.taskmanager.dto.PageResponseDTO;
import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/tasks")
    public PageResponseDTO<TaskResponseDTO> getAllFilteredTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Category category,
            Pageable pageable) {
        return taskService.getAllFilteredTasks(
                title, status, priority, category, pageable);
    }

    @GetMapping("/api/tasks/completed")
    public List<TaskResponseDTO> getCompletedTasks() {
        return taskService.getTasksByStatus(Status.DONE);
    }

    @GetMapping("/api/tasks/completed-query")
    public List<TaskResponseDTO> getCompletedTasksUsingQuery() {
        return taskService.getCompletedTasksUsingQuery();
    }

    @GetMapping("/api/tasks/overdue")
    public List<TaskResponseDTO> findOverdueTasks() {
        return taskService.findOverdueTasksUsingQuery();
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskDtoById(
            @PathVariable Long id) {

        Optional<TaskResponseDTO> dto =
                taskService.getTaskResponseById(id);

        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/api/tasks")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO request) {
        return taskService.addTask(request);
    }

    @PutMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO request) {

        Optional<TaskResponseDTO> updatedTask =
                taskService.updateTask(id, request);

        return updatedTask.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PatchMapping("/api/tasks/{id}/complete")
    public ResponseEntity<TaskResponseDTO> markTaskAsCompleted(
            @PathVariable Long id) {

        Optional<TaskResponseDTO> updatedTask =
                taskService.markTaskAsCompleted(id);

        return updatedTask.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/api/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
