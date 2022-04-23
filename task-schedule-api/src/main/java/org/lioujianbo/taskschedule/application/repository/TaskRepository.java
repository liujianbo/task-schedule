package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
