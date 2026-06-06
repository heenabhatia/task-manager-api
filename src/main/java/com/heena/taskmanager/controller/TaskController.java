package com.heena.taskmanager.controller;

import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Task;
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

    @GetMapping("/hello")
    public String hello() {
        return "Task Manager API is running!";
    }

    @GetMapping("/task")
    public Task getTask() {
        return new Task(
                100L,
                "Buy groceries",
                true
        );
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/completed")
    public List<Task> getCompletedTasks() {
        return taskService.getCompletedTasks(true);
    }

    @GetMapping("/tasks/completed-query")
    public List<Task> getCompletedTasksUsingQuery() {
        return taskService.getCompletedTasksUsingQuery();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);

        if (task.isPresent()) {
            return ResponseEntity.ok(Optional.of(task.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tasks/dto/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskDto(
            @PathVariable Long id) {

        TaskResponseDTO dto =
                taskService.getTaskResponseById(id);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

//    @GetMapping("/tasks/{id}")
//    public Optional<Task> getTaskById(@PathVariable Long id) {
//        return taskService.getTaskById(id);
//    }

    @PostMapping("/task")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody Task task) {

        Optional<Task> updatedTask =
                taskService.updateTask(id, task);

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

//    @GetMapping("/tasks")
//    public List<Task> getAllTasks() {
//        return tasks;
//    }
//
//    @PostMapping("/task")
//    public Task createTask(@RequestBody Task task) {
//        tasks.add(task);
//        return task;
//    }
}
