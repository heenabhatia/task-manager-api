package com.heena.taskmanager.dto;

import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    @Null
    private Category category;
    private Priority priority;
    private Status status;
    private LocalDate dueDate;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

}