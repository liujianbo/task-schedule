package org.lioujianbo.taskschedule.interfaces.controller;

import org.lioujianbo.taskschedule.application.service.TaskNodeService;
import org.lioujianbo.taskschedule.common.dto.CommonResponse;
import org.lioujianbo.taskschedule.model.TaskNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
public class TaskNodeController {
    @Autowired
    private TaskNodeService taskNodeService;

    @PostMapping
    public CommonResponse<TaskNode> saveTaskNode (@RequestBody TaskNode taskNode) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        return builder.data(taskNodeService.saveTaskNode(taskNode)).build();
    }

    @GetMapping("/{id}")
    public CommonResponse<TaskNode> getTaskNode (@PathVariable Integer id) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        return builder.data(taskNodeService.getTaskNode(id)).build();
    }
}
