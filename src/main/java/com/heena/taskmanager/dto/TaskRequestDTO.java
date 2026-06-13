package com.heena.taskmanager.dto;

import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TaskRequestDTO {

    @NotBlank
    private String title;
    private String description;
    private Priority priority;
    private Status status;

}
