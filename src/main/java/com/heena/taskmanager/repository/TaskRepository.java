package com.heena.taskmanager.repository;

import com.heena.taskmanager.model.Status;
import com.heena.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);

    @Query("SELECT t FROM Task t WHERE t.status = 'DONE'")
    List<Task> findCompletedTasks();

}
