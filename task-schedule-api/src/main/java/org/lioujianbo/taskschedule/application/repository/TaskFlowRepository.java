package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.TaskFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFlowRepository extends JpaRepository<TaskFlow, Integer> {
}
