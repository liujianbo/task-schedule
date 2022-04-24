package org.lioujianbo.taskschedule.interfaces.dto.request;

import lombok.Data;

@Data
public class FlowNode {
    private String nodeType;
    private String taskIds;
}
