package com.heena.taskmanager.controller;

import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskResponseDTO responseDTO;
    private TaskRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // initialize common TaskResponseDTO object
        responseDTO = new TaskResponseDTO(1L, "Controller Unit Test",
                "Creating unit test cases for Task controller class",
                Category.LEARNING, Priority.HIGH, Status.IN_PROGRESS, LocalDate.now());

        // initialize common TaskRequestDTO object
        requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Controller Unit Test");
        requestDTO.setDescription("Creating unit test cases for Task controller class");
        requestDTO.setCategory(Category.LEARNING);
        requestDTO.setPriority(Priority.HIGH);
        requestDTO.setStatus(Status.IN_PROGRESS);
        requestDTO.setDueDate(LocalDate.now());
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // arrange
        //when(taskService.getAllTasks()).thenReturn(List.of(responseDTO));

        // act
        //List<TaskResponseDTO> responses = taskController.getAllTasks();

        // assert
//        assertEquals(1, responses.size());
//        assertEquals("Controller Unit Test", responses.get(0).getTitle());
    }

    @Test
    void getCompletedTasks_ShouldReturnDoneTasks() {
        // arrange

        // act

        // assert
    }

    @Test
    void getCompletedTasksUsingQuery_ShouldReturnCompletedTasks() {
        // arrange

        // act

        // assert
    }

    @Test
    void findOverdueTasks_ShouldReturnOverdueTasks() {
        // arrange

        // act

        // assert
    }

    @Test
    void getTaskDtoById_WhenTaskExists_ShouldReturnOkResponse() {
        // arrange
        when(taskService.getTaskResponseById(1L)).thenReturn(Optional.of(responseDTO));

        // act
        ResponseEntity<TaskResponseDTO> response = taskController.getTaskDtoById(1L);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(responseDTO.getTitle(), response.getBody().getTitle());
    }

    @Test
    void getTaskDtoById_WhenTaskDoesNotExist_ShouldReturnNotFoundResponse() {
        // arrange
        when(taskService.getTaskResponseById(999L)).thenReturn(Optional.empty());

        // act
        ResponseEntity<TaskResponseDTO> response = taskController.getTaskDtoById(999L);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        // arrange
        when(taskService.addTask(requestDTO)).thenReturn(responseDTO);

        // act
        TaskResponseDTO response = taskController.createTask(requestDTO);

        // assert
        assertEquals(Category.LEARNING, response.getCategory());
        assertNotNull(response);
        assertEquals(responseDTO.getTitle(), response.getTitle());
    }

    @Test
    void updateTask_WhenTaskExists_ShouldReturnOkResponse() {
        // arrange
        when(taskService.updateTask(1L, requestDTO)).thenReturn(Optional.of(responseDTO));

        // act
        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(1L, requestDTO);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateTask_WhenTaskDoesNotExist_ShouldReturnNotFoundResponse() {
        // arrange
        when(taskService.updateTask(9L, requestDTO)).thenReturn(Optional.empty());

        // act
        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(9L, requestDTO);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteTask_WhenTaskExists_ShouldReturnNoContentResponse() {
        // arrange
        when(taskService.deleteTask(1L)).thenReturn(true);

        // act
        ResponseEntity<Void> result = taskController.deleteTask(1L);

        // assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

    }

    @Test
    void deleteTask_WhenTaskDoesNotExist_ShouldReturnNotFoundResponse() {
        // arrange
        when(taskService.deleteTask(999L)).thenReturn(false);

        // act
        ResponseEntity<Void> result = taskController.deleteTask(999L);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void findTasksByTitle_ShouldReturnMatchingTasks() {
        // arrange

        // act

        // assert
    }

    @Test
    void findTasksByTitleAndCategory_ShouldReturnMatchingTasks() {
        // arrange

        // act

        // assert
    }
}