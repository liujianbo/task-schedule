package org.lioujianbo.taskschedule.execute.service;

import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.application.repository.TaskInsRepository;
import org.lioujianbo.taskschedule.application.repository.TaskRepository;
import org.lioujianbo.taskschedule.infrastructure.constants.ExecStatus;
import org.lioujianbo.taskschedule.infrastructure.utils.ApplicationContextUtil;
import org.lioujianbo.taskschedule.model.Task;
import org.lioujianbo.taskschedule.model.TaskIns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@DependsOn("applicationContextUtil")
public class TaskExecuteService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskInsRepository taskInsRepository;

    public TaskIns exec (Integer taskId, Integer niId) {
        Task task = taskRepository.findById(taskId).get();

        TaskIns taskIns = new TaskIns();
        taskIns.setTaskId(task.getTaskId());
        taskIns.setNiId(niId);
        taskIns.setStartTime(new Date());
        taskIns.setExecStatus(ExecStatus.RUNNING.getStatus());
        taskIns = taskInsRepository.save(taskIns);

        TaskExecutor taskExecutor = getTaskExecutor(task.getTaskType());
        taskExecutor.exec(task, niId, taskIns.getTiId());
        return taskIns;
    }

    private TaskExecutor getTaskExecutor(String taskType) {
        switch (taskType){
            case "ECHO" :
                return (TaskExecutor) ApplicationContextUtil.getObject("echoTaskExecutor");
            case "WAIT" :
                return (TaskExecutor) ApplicationContextUtil.getObject("waitTaskExecutor");
            case "EXCEPTION" :
                return (TaskExecutor) ApplicationContextUtil.getObject("exceptionTaskExecutor");
            default :
                return (TaskExecutor) ApplicationContextUtil.getObject("defaultTaskExecutor");
        }
    }
}
