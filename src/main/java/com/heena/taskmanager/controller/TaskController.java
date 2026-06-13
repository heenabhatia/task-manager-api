package com.heena.taskmanager.controller;

import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.service.TaskService;
import jakarta.validation.Valid;
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

    @GetMapping("v2/tasks")
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/v2/tasks/completed")
    public List<TaskResponseDTO> getCompletedTasks() {
        return taskService.getTasksByStatus(Status.DONE);
    }

    @GetMapping("/v2/tasks/completed-query")
    public List<TaskResponseDTO> getCompletedTasksUsingQuery() {
        return taskService.getCompletedTasksUsingQuery();
    }

    @GetMapping("/v2/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskDtoById(
            @PathVariable Long id) {

        Optional<TaskResponseDTO> dto =
                taskService.getTaskResponseById(id);

        if (dto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto.get());
    }

    @PostMapping("/v2/task")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO request) {
        return taskService.addTask(request);
    }

    @PutMapping("/v2/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask2(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO request) {

        Optional<TaskResponseDTO> updatedTask =
                taskService.updateTask(id, request);

        if (updatedTask.isPresent()) {
            return ResponseEntity.ok(updatedTask.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        boolean deleted = taskService.deleteTask(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
