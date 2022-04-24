package org.lioujianbo.taskschedule.interfaces.dto.response;

import lombok.Data;
import org.lioujianbo.taskschedule.model.Task;

import java.util.Date;
import java.util.List;

@Data
public class TaskFlowNode {
    private Integer nodeId;
    private List<Task> tasks;
    private String nodeType;
    private Integer prevNode;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
