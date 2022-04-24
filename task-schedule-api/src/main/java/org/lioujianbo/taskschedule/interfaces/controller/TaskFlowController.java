package org.lioujianbo.taskschedule.interfaces.controller;

import org.lioujianbo.taskschedule.application.service.TaskFlowService;
import org.lioujianbo.taskschedule.common.dto.CommonResponse;
import org.lioujianbo.taskschedule.interfaces.dto.request.TaskFlowRequest;
import org.lioujianbo.taskschedule.interfaces.dto.response.TaskFlowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow")
public class TaskFlowController {

    @Autowired
    private TaskFlowService taskFlowService;

    @PostMapping
    public CommonResponse<TaskFlowResponse> addTaskFlow (@RequestBody TaskFlowRequest taskFlowRequest) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        return builder.data(taskFlowService.saveTaskFlow(taskFlowRequest)).build();
    }

    @GetMapping("/{id}")
    public CommonResponse<TaskFlowResponse> getTaskFlow (@PathVariable Integer id) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        return builder.data(taskFlowService.getTaskFlowDetail(id)).build();
    }
}
