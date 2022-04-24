package org.lioujianbo.taskschedule.execute.service;

import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.infrastructure.constants.ExecStatus;
import org.lioujianbo.taskschedule.model.Task;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EchoTaskExecutor extends AbstractTaskExecutor{

    @Override
    @Async("asyncTaskExecutor")
    public void exec(Task task, Integer niId, Integer tiId) {
        System.out.println(task.getTaskContent());
        afterExec(task, tiId, niId, ExecStatus.SUCCESS.getStatus(), null);
    }

}
