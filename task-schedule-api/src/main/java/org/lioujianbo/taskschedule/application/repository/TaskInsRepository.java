package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.TaskIns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskInsRepository extends JpaRepository<TaskIns, Integer> {
}
