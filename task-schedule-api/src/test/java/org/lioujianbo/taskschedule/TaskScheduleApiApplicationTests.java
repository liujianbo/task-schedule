package org.lioujianbo.taskschedule;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lioujianbo.taskschedule.execute.service.TaskExecuteService;
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

	@Test
	void taskExec () throws InterruptedException {
		Integer taskId = 3;
		taskExecuteService.exec(taskId, null);
		Thread.sleep(20000);
	}

}
