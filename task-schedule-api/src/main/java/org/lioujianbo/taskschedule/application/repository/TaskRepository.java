package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByTaskIdIn(List<Integer> taskIds);
}
