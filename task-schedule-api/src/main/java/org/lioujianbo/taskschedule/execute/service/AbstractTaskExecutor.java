package org.lioujianbo.taskschedule.execute.service;

import org.lioujianbo.taskschedule.application.repository.TaskInsRepository;
import org.lioujianbo.taskschedule.model.Task;
import org.lioujianbo.taskschedule.model.TaskIns;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class AbstractTaskExecutor implements TaskExecutor {

    TaskInsRepository taskInsRepository;

    @Autowired
    public final void setTaskInsRepository(TaskInsRepository taskInsRepository) {
        this.taskInsRepository = taskInsRepository;
    }

    TaskNodeExecuteService taskNodeExecuteService;

    @Autowired
    public final void setTaskNodeExecuteService(TaskNodeExecuteService taskNodeExecuteService) {
        this.taskNodeExecuteService = taskNodeExecuteService;
    }

    void afterExec(Task task, Integer tiId, Integer niId, String execStatus, String errorMsg) {
        TaskIns taskIns = getTaskIns(tiId);
        taskIns.setEndTime(new Date());
        taskIns.setExecStatus(execStatus);
        taskIns.setErrorMsg(errorMsg);

        taskInsRepository.save(taskIns);
        if (niId != null) {
            taskNodeExecuteService.afterExec(task, niId, execStatus, errorMsg);
        }
    }

    public TaskIns getTaskIns (Integer tiId) {
        return taskInsRepository.findById(tiId).get();
    }


}
