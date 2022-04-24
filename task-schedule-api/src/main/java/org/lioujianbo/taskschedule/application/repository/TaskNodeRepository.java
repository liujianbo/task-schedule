package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.TaskNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskNodeRepository extends JpaRepository<TaskNode, Integer> {
    List<TaskNode> findByFlowIdAndFlowVersion(Integer flowId, Integer flowVersion);
}
