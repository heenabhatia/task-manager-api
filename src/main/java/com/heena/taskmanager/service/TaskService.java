package com.heena.taskmanager.service;

import com.heena.taskmanager.dto.TaskResponseDTO;
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

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getCompletedTasks(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> getCompletedTasksUsingQuery() {
        return taskRepository.findCompletedTasks();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {

        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {

            Task task = existingTask.get();

            task.setTitle(updatedTask.getTitle());
            task.setCompleted(updatedTask.isCompleted());

            return Optional.of(taskRepository.save(task));
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

    public TaskResponseDTO getTaskResponseById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            return null;
        }

        Task t = task.get();

        return new TaskResponseDTO(
                t.getId(),
                t.getTitle(),
                t.isCompleted()
        );
    }

//    public Task getTaskById(Long id) {
//        return taskRepository.findById(id)
//                .orElseThrow();
//    }

    //private final List<Task> tasks = new ArrayList<>();

//    public Task addTask(Task task) {
//        tasks.add(task);
//        return task;
//    }

//    public List<Task> getAllTasks() {
//        return tasks;
//    }
}