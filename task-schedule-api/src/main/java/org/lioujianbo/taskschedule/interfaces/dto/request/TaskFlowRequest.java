package org.lioujianbo.taskschedule.interfaces.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TaskFlowRequest {
    private Integer flowId;
    private String flowName;
    private String flowDescription;
    private List<FlowNode> flowNodes;
}
