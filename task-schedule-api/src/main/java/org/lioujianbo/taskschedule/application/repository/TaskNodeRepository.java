package org.lioujianbo.taskschedule.application.repository;

import org.lioujianbo.taskschedule.model.TaskNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskNodeRepository extends JpaRepository<TaskNode, Integer> {
    List<TaskNode> findByFlowIdAndFlowVersion(Integer flowId, Integer flowVersion);

    @Query("select tn from TaskFlow tf left join TaskNode tn on tf.flowId = tn.flowId and tf.flowVersion = tn.flowVersion and tf.flowId = ?1 and tn.prevNode is null")
    List<TaskNode> findFirstNode (Integer flowId);

    @Query("select tn from TaskFlow tf left join TaskNode tn on tf.flowId = tn.flowId and tf.flowVersion = tn.flowVersion and tf.flowId = ?1 and tn.prevNode = ?2")
    List<TaskNode> findNextNode (Integer flowId, Integer prevNode);
}
