package org.lioujianbo.taskschedule.interfaces.controller;

import org.lioujianbo.taskschedule.common.dto.CommonResponse;
import org.lioujianbo.taskschedule.execute.service.TaskExecuteService;
import org.lioujianbo.taskschedule.execute.service.TaskFlowExecuteService;
import org.lioujianbo.taskschedule.interfaces.dto.request.TaskFlowExecRequest;
import org.lioujianbo.taskschedule.interfaces.dto.request.TaskFlowRequest;
import org.lioujianbo.taskschedule.model.TaskFlowIns;
import org.lioujianbo.taskschedule.model.TaskIns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exec")
public class TaskExecController {

    @Autowired
    private TaskExecuteService taskExecuteService;

    @Autowired
    private TaskFlowExecuteService taskFlowExecuteService;

    @PostMapping("/flow/{flowId}")
    public CommonResponse<TaskFlowIns> startFlow (@RequestBody TaskFlowExecRequest taskFlowExecRequest, @PathVariable Integer flowId) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        TaskFlowIns taskFlowIns = taskFlowExecuteService.exec(flowId, taskFlowExecRequest.getFiId(), taskFlowExecRequest.getNiId());
        return builder.data(taskFlowIns).build();
    }

    @PostMapping("/task/{taskId}")
    public CommonResponse<TaskIns> startTask (@PathVariable Integer taskId) {
        CommonResponse.CommonResponseBuilder builder = new CommonResponse.CommonResponseBuilder();
        TaskIns taskIns = taskExecuteService.exec(taskId, null);
        return builder.data(taskIns).build();
    }
}
