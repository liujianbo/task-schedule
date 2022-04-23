package org.lioujianbo.taskschedule.interfaces.controller;

import org.lioujianbo.taskschedule.application.service.TaskService;
import org.lioujianbo.taskschedule.common.dto.CommonResponse;
import org.lioujianbo.taskschedule.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public CommonResponse<Task> addTask (@RequestBody Task task) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        return builder.data(taskService.saveTask(task)).build();
    }

    @GetMapping("/{id}")
    public CommonResponse<Task> getTask (@PathVariable Integer id) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        return builder.data(taskService.getTask(id)).build();
    }
}
