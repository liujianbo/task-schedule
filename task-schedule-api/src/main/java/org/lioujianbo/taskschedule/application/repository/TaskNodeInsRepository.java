package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.TaskNodeIns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskNodeInsRepository extends JpaRepository<TaskNodeIns, Integer> {
    List<TaskNodeIns> findByFiIdOrderByStartTimeDesc(Integer fiId);
}
