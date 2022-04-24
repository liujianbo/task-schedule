package org.lioujianbo.taskschedule.interfaces.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskFlowResponse {
    private Integer flowId;
    private String flowName;
    private String flowDescription;
    private List<TaskFlowNode> flowNodes;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
