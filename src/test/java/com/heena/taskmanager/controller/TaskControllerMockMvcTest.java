package com.heena.taskmanager.controller;

import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    private TaskRequestDTO requestDTO;
    private TaskResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Learning Unit Testing");
        requestDTO.setDescription("CCreating unit test cases");
        requestDTO.setCategory(Category.LEARNING);
        requestDTO.setPriority(Priority.HIGH);
        requestDTO.setStatus(Status.IN_PROGRESS);
        requestDTO.setDueDate(LocalDate.now());

        responseDTO = new TaskResponseDTO(
                1L,
                "Learning Unit Testing",
                "Creating unit test cases",
                Category.LEARNING,
                Priority.HIGH,
                Status.IN_PROGRESS,
                LocalDate.now()
        );
    }

    @Test
    void getTaskDtoById_WhenTaskExists_ShouldReturn200() throws Exception {

        when(taskService.getTaskResponseById(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/tasks/1")).andExpect(status().isOk()).andExpect(jsonPath("$.title")
                .value("Learning Unit Testing"));
    }

    @Test
    void getTaskDtoById_WhenTaskDoesNotExists_ShouldReturn404() throws Exception {

        when(taskService.getTaskResponseById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/99")).andExpect(status().isNotFound());
    }
    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {

        when(taskService.addTask(any(TaskRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Learning Unit Testing"));
    }

    @Test
    void createTask_WithInvalidTitle_ShouldReturnCreatedTas() throws Exception {

        when(taskService.addTask(any(TaskRequestDTO.class))).thenReturn(responseDTO);

        TaskRequestDTO invalidRequest = new TaskRequestDTO();
        invalidRequest.setTitle(" ");
        invalidRequest.setDescription("CCreating unit test cases");
        invalidRequest.setCategory(Category.LEARNING);
        invalidRequest.setPriority(Priority.HIGH);
        invalidRequest.setStatus(Status.IN_PROGRESS);
        invalidRequest.setDueDate(LocalDate.now());

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTask_WhenTaskExists_ShouldReturnOkResponse() throws Exception {
        // arrange
        when(taskService.updateTask(eq(1L), any(TaskRequestDTO.class)))
                .thenReturn(Optional.of(responseDTO));

        // act
        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Learning Unit Testing"));
    }

    @Test
    void updateTask_WhenTaskDoesNotExist_ShouldReturnNotFoundResponse() throws Exception {
        // arrange
        when(taskService.updateTask(eq(9L), any(TaskRequestDTO.class)))
                .thenReturn(Optional.empty());

        // act
        mockMvc.perform(put("/api/tasks/9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void markTasksAsCompleted_WhenTaskExist_ShouldReturnOkResponse() throws Exception {
        // arrange
        when(taskService.markTaskAsCompleted(1L)).thenReturn(Optional.of(responseDTO));

        // act
        mockMvc.perform(patch("/api/tasks/1/complete")).andExpect(status().isOk());
    }

    @Test
    void markTasksAsCompleted_WhenTaskDoesNotExist_ShouldReturnNotFoundResponse() throws Exception {
        // arrange
        when(taskService.markTaskAsCompleted(99L)).thenReturn(Optional.empty());

        // act
        mockMvc.perform(patch("/api/tasks/99/complete")).andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_WhenTaskExist_ShouldReturnOkResponse() throws Exception {
        // arrange
        when(taskService.deleteTask(1L)).thenReturn(true);

        // act
        mockMvc.perform(delete("/api/tasks/1")).andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_WhenTaskDoesNotExist_ShouldReturnNotFoundResponse() throws Exception {
        // arrange
        when(taskService.deleteTask(99L)).thenReturn(false);

        // act
        mockMvc.perform(delete("/api/tasks/99")).andExpect(status().isNotFound());
    }



}
