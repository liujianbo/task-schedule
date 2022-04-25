package org.lioujianbo.taskschedule;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lioujianbo.taskschedule.execute.service.TaskExecuteService;
import org.lioujianbo.taskschedule.execute.service.TaskNodeExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@EnableAsync
class TaskScheduleApiApplicationTests {

	@Autowired
	TaskExecuteService taskExecuteService;

	@Autowired
	TaskNodeExecuteService taskNodeExecuteService;

	@Test
	void taskExec () throws InterruptedException {
		Integer taskId = 1;
		Integer niId = 4;
		taskExecuteService.exec(taskId, niId);
		Thread.sleep(20000);
	}

}
