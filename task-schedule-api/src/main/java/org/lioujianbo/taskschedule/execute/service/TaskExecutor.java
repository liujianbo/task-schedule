package org.lioujianbo.taskschedule.execute.service;


import org.lioujianbo.taskschedule.model.Task;

public interface TaskExecutor {
    void exec(Task task, Integer niId, Integer tiId);
}
