package org.lioujianbo.taskschedule.application.service;

import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.application.repository.TaskRepository;
import org.lioujianbo.taskschedule.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = "taskCache")
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask (Task task) {
        return taskRepository.save(task);
    }

    public Task getTask (Integer taskId) {
        return taskRepository.findById(taskId).get();
    }
}
