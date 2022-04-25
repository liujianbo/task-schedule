package org.lioujianbo.taskschedule.infrastructure.schedule;

import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.execute.service.TaskExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskExecuteSchedule {

    @Autowired
    TaskExecuteService taskExecuteService;

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void startTask() {
        log.info("start tasks.");
        taskExecuteService.startTask();
    }
}
