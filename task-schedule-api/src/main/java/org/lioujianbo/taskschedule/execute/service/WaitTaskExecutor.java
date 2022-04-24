package org.lioujianbo.taskschedule.execute.service;

import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.infrastructure.constants.ExecStatus;
import org.lioujianbo.taskschedule.model.Task;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WaitTaskExecutor extends AbstractTaskExecutor{

    @Override
    @Async("asyncTaskExecutor")
    public void exec(Task task, Integer niId, Integer tiId) {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            afterExec(task, tiId, niId, ExecStatus.FAILED.getStatus(), e.getMessage());
        }
        log.info("wait task execute cost:{}ms", System.currentTimeMillis() - startTime);
        afterExec(task, tiId, niId, ExecStatus.SUCCESS.getStatus(), null);
    }

}
