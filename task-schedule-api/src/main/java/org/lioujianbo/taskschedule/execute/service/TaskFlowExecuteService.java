package org.lioujianbo.taskschedule.execute.service;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.application.repository.TaskFlowInsRepository;
import org.lioujianbo.taskschedule.application.repository.TaskInsRepository;
import org.lioujianbo.taskschedule.application.repository.TaskNodeInsRepository;
import org.lioujianbo.taskschedule.application.repository.TaskRepository;
import org.lioujianbo.taskschedule.infrastructure.constants.ExecStatus;
import org.lioujianbo.taskschedule.infrastructure.utils.ApplicationContextUtil;
import org.lioujianbo.taskschedule.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@DependsOn("applicationContextUtil")
public class TaskFlowExecuteService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskFlowInsRepository taskFlowInsRepository;

    @Autowired
    private TaskNodeInsRepository taskNodeInsRepository;

    @Autowired
    private TaskNodeExecuteService taskNodeExecuteService;

    public TaskFlowIns exec (Integer flowId, Integer fiId, Integer niId) {
        TaskFlowIns taskFlowIns = new TaskFlowIns();
        if (fiId != null) {
            taskFlowIns = taskFlowInsRepository.findById(fiId).get();
        } else {
            taskFlowIns.setFlowId(flowId);
            taskFlowIns.setStartTime(new Date());
            taskFlowIns.setStatus(ExecStatus.RUNNING.getStatus());

            taskFlowIns = taskFlowInsRepository.save(taskFlowIns);
        }

        startFlowNode(taskFlowIns);

        return taskFlowIns;
    }

    public void startFlowNode (TaskFlowIns taskFlowIns) {
        List<TaskNodeIns> taskNodeInsList = taskNodeInsRepository.findByFiIdOrderByStartTimeDesc(taskFlowIns.getFiId());
        if (CollectionUtils.isEmpty(taskNodeInsList)) {
            taskNodeExecuteService.startFirstNode(taskFlowIns);
        } else {
            TaskNodeIns latelyTaskNodeIns = taskNodeInsList.get(0);
            if (StringUtils.equals(latelyTaskNodeIns.getStatus(), ExecStatus.SUCCESS.getStatus())) {
                taskNodeExecuteService.startNextNode(taskFlowIns, latelyTaskNodeIns);
            } else if (StringUtils.equals(latelyTaskNodeIns.getStatus(), ExecStatus.FAILED.getStatus())) {
                taskNodeExecuteService.restartNode(taskFlowIns, latelyTaskNodeIns);
            }
        }
    }
}
