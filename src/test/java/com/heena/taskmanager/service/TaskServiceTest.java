package com.heena.taskmanager.service;

import com.heena.taskmanager.dto.TaskRequestDTO;
import com.heena.taskmanager.dto.TaskResponseDTO;
import com.heena.taskmanager.model.Category;
import com.heena.taskmanager.model.Priority;
import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.model.Task;
import com.heena.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // initialize common Task object
        task = new Task();
        task.setId(1L);
        task.setTitle("Learning Unit Testing");
        task.setDescription("Creating unit test cases for Task service class");
        task.setCategory(Category.LEARNING);
        task.setPriority(Priority.HIGH);
        task.setStatus(Status.IN_PROGRESS);
        task.setDueDate(LocalDate.now());

        // initialize common TaskRequestDTO object
        requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Learning Unit Testing");
        requestDTO.setDescription("Creating unit test cases for Task service class");
        requestDTO.setCategory(Category.LEARNING);
        requestDTO.setPriority(Priority.HIGH);
        requestDTO.setStatus(Status.IN_PROGRESS);
        requestDTO.setDueDate(LocalDate.now());
    }

    @Test
    void getAllTasks_ShouldReturnListOfTaskResponseDTOs() {
        // arrange
        when(taskRepository.findAll()).thenReturn(List.of(task));

        // act
        List<TaskResponseDTO> result = taskService.getAllTasks();

        // assert
        assertEquals(1, result.size());
        assertEquals("Learning Unit Testing", result.get(0).getTitle());
    }

    @Test
    void getTasksByStatus_ShouldReturnFilteredTasks() {
        // arrange
        when(taskRepository.findByStatus(Status.DONE)).thenReturn(List.of(task));

        // act
        List<TaskResponseDTO> result = taskService.getTasksByStatus(Status.DONE);

        // assert
        assertEquals(1, result.size());
        assertEquals("Learning Unit Testing", result.get(0).getTitle());
        assertEquals(Category.LEARNING, result.get(0).getCategory());
    }

    @Test
    void getCompletedTasksUsingQuery_ShouldReturnCompletedTasks() {
        // arrange
        when(taskRepository.findCompletedTasks()).thenReturn(List.of(task));

        // act
        List<TaskResponseDTO> result = taskService.getCompletedTasksUsingQuery();

        // assert
        assertEquals(1, result.size());
        assertEquals("Learning Unit Testing", result.get(0).getTitle());
        assertEquals(Category.LEARNING, result.get(0).getCategory());
    }

    @Test
    void getTaskResponseById_WhenTaskExists_ShouldReturnTaskResponseDTO() {
        // arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // act
        Optional<TaskResponseDTO> response = taskService.getTaskResponseById(1L);

        // assert
        assertTrue(response.isPresent());
        assertEquals("Learning Unit Testing", response.get().getTitle());
        assertEquals(Category.LEARNING, response.get().getCategory());
    }

    @Test
    void getTaskResponseById_WhenTaskDoesNotExist_ShouldReturnEmptyOptional() {
        // arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // act
        Optional<TaskResponseDTO> response = taskService.getTaskResponseById(999L);

        // assert
        assertTrue(response.isEmpty());
    }

    @Test
    void addTask_ShouldSaveAndReturnTaskResponseDTO() {
        // arrange
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // act
        TaskResponseDTO response = taskService.addTask(requestDTO);

        // assert
        assertEquals("Learning Unit Testing", response.getTitle());
        assertEquals(Category.LEARNING, response.getCategory());
        assertEquals(Priority.HIGH, response.getPriority());

        //verify
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask_WhenTaskExists_ShouldUpdateAndReturnTaskResponseDTO() {
        // arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // act
        Optional<TaskResponseDTO> result = taskService.updateTask(1L, requestDTO);

        // assert
        assertTrue(result.isPresent());
        assertEquals("Learning Unit Testing", result.get().getTitle());
        assertEquals(Category.LEARNING, result.get().getCategory());

    }

    @Test
    void updateTask_WhenTaskDoesNotExist_ShouldReturnEmptyOptional() {
        // arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // act
        Optional<TaskResponseDTO> result = taskService.updateTask(1L, requestDTO);

        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteTask_WhenTaskExists_ShouldDeleteAndReturnTrue() {
        // arrange
        when(taskRepository.existsById(1L)).thenReturn(true);

        // act
        boolean result = taskService.deleteTask(1L);

        // assert
        assertTrue(result);

        //verify
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_WhenTaskDoesNotExist_ShouldReturnFalse() {
        // arrange
        when(taskRepository.existsById(999L)).thenReturn(false);

        // act
        boolean result = taskService.deleteTask(999L);

        // assert
        assertFalse(result);

        //verify
        verify(taskRepository, never()).deleteById(anyLong());
    }
}