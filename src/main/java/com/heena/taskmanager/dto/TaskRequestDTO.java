package com.heena.taskmanager.dto;

import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import jakarta.validation.constraints.NotBlank;

public class TaskRequestDTO {

    @NotBlank
    private String title;
    private String description;
    private Priority priority;
    private Status status;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, Priority priority, Status status) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
