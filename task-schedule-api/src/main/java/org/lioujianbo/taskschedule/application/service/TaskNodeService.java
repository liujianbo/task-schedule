package org.lioujianbo.taskschedule.application.service;

import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.application.repository.TaskNodeRepository;
import org.lioujianbo.taskschedule.model.TaskNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = "taskNodeCache")
public class TaskNodeService {

    @Autowired
    private TaskNodeRepository taskNodeRepository;

    public TaskNode saveTaskNode (TaskNode taskNode) {
        return taskNodeRepository.save(taskNode);
    }

    public TaskNode getTaskNode (Integer nodeId) {
        return taskNodeRepository.findById(nodeId).get();
    }
}
