package org.lioujianbo.taskschedule.execute.service;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.application.repository.TaskFlowInsRepository;
import org.lioujianbo.taskschedule.application.repository.TaskNodeInsRepository;
import org.lioujianbo.taskschedule.application.repository.TaskNodeRepository;
import org.lioujianbo.taskschedule.infrastructure.constants.ExecStatus;
import org.lioujianbo.taskschedule.infrastructure.utils.RedisUtils;
import org.lioujianbo.taskschedule.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@DependsOn("applicationContextUtil")
public class TaskNodeExecuteService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TaskNodeRepository taskNodeRepository;

    @Autowired
    private TaskNodeInsRepository taskNodeInsRepository;

    @Autowired
    private TaskFlowInsRepository taskFlowInsRepository;

    public void startFirstNode(TaskFlowIns taskFlowIns) {
        List<TaskNode> taskNodes = taskNodeRepository.findFirstNode(taskFlowIns.getFlowId());
        if (!CollectionUtils.isEmpty(taskNodes)) {
            TaskNode taskNode = taskNodes.get(0);
            List<TaskNode> nextNode = taskNodeRepository.findNextNode(taskFlowIns.getFlowId(), taskNode.getNodeId());
            submitNodeTask(taskFlowIns, nextNode.get(0));
        }
    }

    public void startNextNode(TaskFlowIns taskFlowIns, TaskNodeIns latelyTaskNodeIns) {
        TaskNode taskNode = taskNodeRepository.findById(latelyTaskNodeIns.getNodeId()).get();
        List<TaskNode> nextNode = taskNodeRepository.findNextNode(taskFlowIns.getFlowId(), taskNode.getNodeId());
        submitNodeTask(taskFlowIns, nextNode.get(0));
    }

    public void restartNode(TaskFlowIns taskFlowIns, TaskNodeIns latelyTaskNodeIns) {
        TaskNode taskNode = taskNodeRepository.findById(latelyTaskNodeIns.getNodeId()).get();
        submitNodeTask(taskFlowIns, taskNode);
    }

    public void submitNodeTask (TaskFlowIns taskFlowIns, TaskNode taskNode) {
        if (StringUtils.equals(taskNode.getNodeType(), "END")) {
            taskFlowIns.setStatus(ExecStatus.SUCCESS.getStatus());
            taskFlowIns.setEndTime(new Date());

            taskFlowInsRepository.save(taskFlowIns);
        } else if (StringUtils.equals(taskNode.getNodeType(), "TASK")) {
            TaskNodeIns taskNodeIns = new TaskNodeIns();
            taskNodeIns.setNodeId(taskNode.getNodeId());
            taskNodeIns.setFiId(taskFlowIns.getFiId());
            taskNodeIns.setStartTime(new Date());
            taskNodeIns.setStatus(ExecStatus.RUNNING.getStatus());

            taskNodeIns = taskNodeInsRepository.save(taskNodeIns);

            if (!StringUtils.isEmpty(taskNode.getTaskIds())) {
                List<Integer> taskIdList = Stream.of(taskNode.getTaskIds().split(",")).map(Integer::valueOf).collect(Collectors.toList());
                for (Integer taskId : taskIdList) {
                    redisUtils.setIfAbsent("TASK:" + taskNodeIns.getNiId() + ":" + taskId, "NEW");
                }
            }
        }
    }

    public void afterExec(Task task, Integer niId, String execStatus, String errorMsg) {
        TaskNodeIns taskNodeIns = taskNodeInsRepository.findById(niId).get();
        taskNodeIns.setEndTime(new Date());
        taskNodeIns.setStatus(execStatus);
        taskNodeIns.setErrorMsg(errorMsg);

        taskNodeInsRepository.save(taskNodeIns);

        redisUtils.del("TASK:" + taskNodeIns.getNiId() + ":" + task.getTaskId());

        TaskFlowIns taskFlowIns = taskFlowInsRepository.findById(taskNodeIns.getFiId()).get();
        if (StringUtils.equals(execStatus, ExecStatus.SUCCESS.getStatus())) {
            startNextNode(taskFlowIns, taskNodeIns);
        } else {
            taskFlowIns.setStatus(execStatus);
            taskFlowIns.setEndTime(new Date());
            taskFlowIns.setErrorMsg(errorMsg);

            taskFlowInsRepository.save(taskFlowIns);
        }
    }
}
