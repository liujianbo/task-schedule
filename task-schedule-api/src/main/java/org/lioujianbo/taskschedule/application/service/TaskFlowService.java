package org.lioujianbo.taskschedule.application.service;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.lioujianbo.taskschedule.application.repository.TaskFlowRepository;
import org.lioujianbo.taskschedule.application.repository.TaskNodeRepository;
import org.lioujianbo.taskschedule.application.repository.TaskRepository;
import org.lioujianbo.taskschedule.interfaces.dto.request.FlowNode;
import org.lioujianbo.taskschedule.interfaces.dto.request.TaskFlowRequest;
import org.lioujianbo.taskschedule.interfaces.dto.response.TaskFlowNode;
import org.lioujianbo.taskschedule.interfaces.dto.response.TaskFlowResponse;
import org.lioujianbo.taskschedule.model.Task;
import org.lioujianbo.taskschedule.model.TaskFlow;
import org.lioujianbo.taskschedule.model.TaskNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@CacheConfig(cacheNames = "taskFlowCache")
public class TaskFlowService {

    @Autowired
    private TaskFlowRepository taskFlowRepository;

    @Autowired
    private TaskNodeRepository taskNodeRepository;

    @Autowired
    private TaskRepository taskRepository;

    public TaskFlowResponse saveTaskFlow (TaskFlowRequest taskFlowRequest) {
        TaskFlow taskFlow = new TaskFlow();
        if (taskFlowRequest.getFlowId() != null) {
            taskFlow = getTaskFlow(taskFlowRequest.getFlowId());
        }
        BeanUtils.copyProperties(taskFlowRequest, taskFlow);
        if (taskFlow.getFlowVersion() == null) {
            taskFlow.setFlowVersion(1);
        } else {
            taskFlow.setFlowVersion(taskFlow.getFlowVersion() + 1);
        }
        taskFlow = saveTaskFlow(taskFlow);

        List<TaskFlowNode> taskNodes = saveTaskNodes(taskFlow, taskFlowRequest.getFlowNodes());
        TaskFlowResponse taskFlowResponse = new TaskFlowResponse();
        BeanUtils.copyProperties(taskFlow, taskFlowResponse);
        taskFlowResponse.setFlowNodes(taskNodes);
        return taskFlowResponse;
    }

    public TaskFlow saveTaskFlow (TaskFlow taskFlow) {
        return taskFlowRepository.save(taskFlow);
    }

    public List<TaskFlowNode> saveTaskNodes (TaskFlow taskFlow, List<FlowNode> flowNodes) {
        List<TaskFlowNode> taskNodes = new ArrayList<>();
        Integer prevNodeId = null;
        for (FlowNode flowNode : flowNodes) {
            TaskNode taskNode = new TaskNode();
            BeanUtils.copyProperties(flowNode, taskNode);
            taskNode.setFlowId(taskFlow.getFlowId());
            taskNode.setFlowVersion(taskFlow.getFlowVersion());
            taskNode.setPrevNode(prevNodeId);
            taskNode = taskNodeRepository.save(taskNode);

            prevNodeId = taskNode.getNodeId();
            TaskFlowNode taskFlowNode = new TaskFlowNode();
            BeanUtils.copyProperties(taskNode, taskFlowNode);
            if (!StringUtils.isEmpty(taskNode.getTaskIds())) {
                taskFlowNode.setTasks(getTasks(taskNode.getTaskIds()));
            }
            taskNodes.add(taskFlowNode);
        }
        return taskNodes;
    }

    public TaskFlowResponse getTaskFlowDetail (Integer flowId) {
        TaskFlow taskFlow = getTaskFlow(flowId);
        List<TaskFlowNode> taskFlowNodes = getFlowNodes(taskFlow);
        TaskFlowResponse taskFlowResponse = new TaskFlowResponse();
        BeanUtils.copyProperties(taskFlow, taskFlowResponse);
        taskFlowResponse.setFlowNodes(taskFlowNodes);
        return taskFlowResponse;
    }

    public TaskFlow getTaskFlow (Integer flowId) {
        return taskFlowRepository.findById(flowId).get();
    }

    public List<TaskFlowNode> getFlowNodes (TaskFlow taskFlow) {
        List<TaskFlowNode> taskFlowNodes = new ArrayList<>();
        List<TaskNode> taskNodes = taskNodeRepository.findByFlowIdAndFlowVersion(taskFlow.getFlowId(), taskFlow.getFlowVersion());
        for (TaskNode taskNode : taskNodes) {
            TaskFlowNode taskFlowNode = new TaskFlowNode();
            BeanUtils.copyProperties(taskNode, taskFlowNode);
            if (!StringUtils.isEmpty(taskNode.getTaskIds())) {
                taskFlowNode.setTasks(getTasks(taskNode.getTaskIds()));
            }
            taskFlowNodes.add(taskFlowNode);
        }
        return taskFlowNodes;
    }

    public List<Task> getTasks (String taskIds) {
        List<Integer> taskIdList = Stream.of(taskIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        return taskRepository.findByTaskIdIn(taskIdList);
    }
}
