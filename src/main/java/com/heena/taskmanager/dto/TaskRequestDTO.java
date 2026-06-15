package com.heena.taskmanager.dto;

import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskRequestDTO {

    @NotBlank
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private Status status;
    private LocalDate dueDate;
}
