package org.lioujianbo.taskschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TaskScheduleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskScheduleApiApplication.class, args);
	}

}
