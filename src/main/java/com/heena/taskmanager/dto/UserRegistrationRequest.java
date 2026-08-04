package com.heena.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegistrationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
