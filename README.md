# task-schedule
LiveRamp Cast Test 

# 主要功能说明

## 已实现

1. 通过 REST API 中的，save task 和 save task flow 接口可以对任务进行编排
2. task flow 和 task node 供任务编排使用，具体执行任务在 task 中进行配置
3. 目前 task 有 ECHO;WAIT;EXCEPTION 几种测试用的实现
4. 通过 start flow 接口可以启动一个任务流程实例，流程节点会按顺序执行
5. 节点执行时，将任务提交分布式缓存，从而实现服务支持横向拓展
6. 定时轮询缓存中待执行任务，取得任务后清除缓存并启动任务执行
7. 任务执行完成后，更新任务状态、节点状态及流程状态

## 待实现

1. task node 可以设置节点完成条件（当前实现为节点中任意 task 执行成功即可向下执行）
2. task flow 任务状态监控

# 开发环境
- 语言：Java 8
- 依赖管理：Maven
- 数据库：MySQL
- 缓存：Redis

# core function
1. task schedule maintain
2. task flow execute
3. task flow monitor
4. distribute deploy support

# module
## REST API
1. create/update/delete task flow
   ```
   save task flow
   request url: POST /flow
   request body:
   {
    "flowId":1,
    "flowName":"first flow",
    "flowDescription":"first flow for test1",
    "flowNodes":[
            {
                "nodeType":"START"
            },
            {
                "nodeType":"TASK",
                "taskIds":"1"
            },
            {
                "nodeType":"TASK",
                "taskIds":"2"
            },
            {
                "nodeType":"TASK",
                "taskIds":"3"
            },
            {
                "nodeType":"TASK",
                "taskIds":"1"
            },
            {
                "nodeType":"END"
            }
        ]
    }
   
   response:
   {
        "code": "200",
        "message": "SUCCESS",
        "data": {
            "flowId": 1,
            "flowName": "first flow",
            "flowDescription": "first flow for test1",
            "flowNodes": [
                {
                    "nodeId": 41,
                    "nodeType": "START",
                    "createTime": "2022-04-25 09:50:12",
                    "updateTime": "2022-04-25 09:50:12"
                },
                {
                    "nodeId": 42,
                    "tasks": [
                        {
                            "createTime": "2022-04-24 11:35:39",
                            "updateTime": "2022-04-24 19:28:53",
                            "taskId": 1,
                            "taskName": "first task1",
                            "taskType": "ECHO"
                        }
                    ],
                    "nodeType": "TASK",
                    "prevNode": 41,
                    "createTime": "2022-04-25 09:50:12",
                    "updateTime": "2022-04-25 09:50:12"
                },
                {
                    "nodeId": 43,
                    "tasks": [
                        {
                            "createTime": "2022-04-24 19:29:46",
                            "updateTime": "2022-04-24 19:29:46",
                            "taskId": 2,
                            "taskName": "wait task1",
                            "taskType": "WAIT"
                        }
                    ],
                    "nodeType": "TASK",
                    "prevNode": 42,
                    "createTime": "2022-04-25 09:50:12",
                    "updateTime": "2022-04-25 09:50:12"
                },
                {
                    "nodeId": 44,
                    "tasks": [
                        {
                            "createTime": "2022-04-24 19:30:04",
                            "updateTime": "2022-04-24 19:30:04",
                            "taskId": 3,
                            "taskName": "exception task1",
                            "taskType": "EXCEPTION"
                        }
                    ],
                    "nodeType": "TASK",
                    "prevNode": 43,
                    "createTime": "2022-04-25 09:50:12",
                    "updateTime": "2022-04-25 09:50:12"
                },
                {
                    "nodeId": 45,
                    "tasks": [
                        {
                            "createTime": "2022-04-24 11:35:39",
                            "updateTime": "2022-04-24 19:28:53",
                            "taskId": 1,
                            "taskName": "first task1",
                            "taskType": "ECHO"
                        },
                        {
                            "createTime": "2022-04-24 19:29:46",
                            "updateTime": "2022-04-24 19:29:46",
                            "taskId": 2,
                            "taskName": "wait task1",
                            "taskType": "WAIT"
                        },
                        {
                            "createTime": "2022-04-24 19:30:04",
                            "updateTime": "2022-04-24 19:30:04",
                            "taskId": 3,
                            "taskName": "exception task1",
                            "taskType": "EXCEPTION"
                        }
                    ],
                    "nodeType": "TASK",
                    "prevNode": 44,
                    "createTime": "2022-04-25 09:50:12",
                    "updateTime": "2022-04-25 09:50:12"
                },
                {
                    "nodeId": 46,
                    "nodeType": "END",
                    "prevNode": 45,
                    "createTime": "2022-04-25 09:50:12",
                    "updateTime": "2022-04-25 09:50:12"
                }
            ],
            "createTime": "2022-04-24 13:57:59",
            "updateTime": "2022-04-25 09:50:12"
        }
    }
   
   task flow detail
   request url: GET /flow/{flowId}
   response:
   same as above
   ```
2. create/update/delete task
   ```
   save task
   request url: POST /task
   request body:
   {
        "taskName":"exception task1",
        "taskType":"EXCEPTION",
        "taskContent":"task content"
    }
   
   response:
   {
        "code": "200",
        "message": "SUCCESS",
        "data": {
            "createTime": "2022-04-24 19:30:04",
            "updateTime": "2022-04-24 19:30:04",
            "taskId": 3,
            "taskName": "exception task1",
            "taskType": "EXCEPTION",
            "taskContent":"task content"
        }
    }
   
   task detail
   request url: GET /task/{taskId}
   response:
   {
        "code": "200",
        "message": "SUCCESS",
        "data": {
            "createTime": "2022-04-24 19:30:04",
            "updateTime": "2022-04-24 19:30:04",
            "taskId": 3,
            "taskName": "exception task1",
            "taskType": "EXCEPTION",
            "taskContent":"task content"
        }
    }
   ```
3. task flow execute
    ```
    start flow
    request url: POST /exec/flow/{flowId}
      
    retry flow
    request url: POST /exec/flow/{flowId}
    request body:
    {
        "fiId":1, //
        "niId":1
    }
      
    start task
    request url: POST /exec/task/{taskId}
    ```
4. task flow monitor (TODO)
   ```
    task flow execute history
   request url: POST /monitor/flow
   request body:
   {
        "flowId":1, //optional, filter condition
        "fiId":1 //optional
    }
   
   response:
   {
        "code": "200",
        "message": "SUCCESS",
        "data": [{
            "fiId":1,
            "flowId": 1,
            "flowName": "first flow",
            "startTime": "2022-04-24 19:30:04",
            "endTime": "2022-04-24 19:30:04"
            "status": "SUCCESS",
            "errorMsg": "",
            "createTime": "2022-04-24 19:30:04",
            "updateTime": "2022-04-24 19:30:04"
        },...]
    }
   
   task flow execute history detail
   request url: POST /monitor/flow/detail/{fiId}
   response:
   {
        "code": "200",
        "message": "SUCCESS",
        "data": [{
            "niId":1,
            "fiId": 1,
            "startTime": "2022-04-24 19:30:04",
            "endTime": "2022-04-24 19:30:04"
            "status": "SUCCESS",
            "errorMsg": "",
            "createTime": "2022-04-24 19:30:04",
            "updateTime": "2022-04-24 19:30:04"
        },...]
    }
   ```
## schedule executor
1. 启动任务后自动寻找开始节点，开始任务执行
2. 节点任务同时提交分布式缓存(可替换为MQ)，更新节点状态为执行中
3. 定时获取(MQ消费)待执行任务，并开始执行
4. 任务执行完成后，更新节点状，并执行下一节点任务（失败或结束时，更新流程状态）
5. 节点存在多个 task 时，需要满足完成指定的任务数才能往下继续执行（TODO，当前实现为其中一个任务成功即可向下执行）

# data module
## task_flow
| col | type | comments |
|-----|----------------------|----------------------------------|
| flow_id | bigint |  |
| flow_name | varchar |  |
| flow_description | varchar  |  |
| flow_version | bigint | 版本号，更新后版本自动+1 |
| create_time | timestamp |  |
| create_user | varchar |  |
| update_time | timestamp |  |
| update_user | varchar |  |

## task_node
| col | type | comments |
|-----|----------------------|----------------------------------|
| node_id | bigint |  |
| flow_id | bigint |  |
| flow_version | bigint  | flow 更新时直接新增新的节点，并设置成最新的 version |
| node_type | varchar | 节点类型：START;TASK;END 等 |
| task_ids | varchar | 节点任务id，多个任务以 , 分隔 |
| prev_node | bigint | 前继节点id |
| create_time | timestamp |  |
| create_user | varchar |  |
| update_time | timestamp |  |
| update_user | varchar |  |

## task
| col | type | comments |
|-----|----------------------|----------------------------------|
| task_id | bigint |  |
| task_name | varchar |  |
| task_description | varchar  |  |
| task_type | varchar | 任务类型：SHELL;ECHO;WAIT 等，按类型实现具体执行方法 |
| task_config | varchar | 任务配置 |
| task_content | varchar | 任务内容 |
| create_time | timestamp |  |
| create_user | varchar |  |
| update_time | timestamp |  |
| update_user | varchar |  |

## task_flow_ins
| col | type | comments |
|-----|----------------------|----------------------------------|
| fi_id | bigint |  |
| flow_id | bigint |  |
| start_time | timestamp |  |
| end_time | timestamp |  |
| status | varchar | flow 执行状态，RUNNING;SUCCESS;FAILED |
| error_msg | varchar | 失败信息 |
| create_time | timestamp |  |
| create_user | varchar |  |
| update_time | timestamp |  |
| update_user | varchar |  |

## task_node_ins
| col | type | comments |
|-----|----------------------|----------------------------------|
| ni_id | bigint |  |
| fi_id | bigint |  |
| node_id | bigint |  |
| start_time | timestamp |  |
| end_time | timestamp |  |
| status | varchar | node 执行状态，RUNNING;SUCCESS;FAILED |
| error_msg | varchar | 失败信息 |
| create_time | timestamp |  |
| create_user | varchar |  |
| update_time | timestamp |  |
| update_user | varchar |  |

## task_ins
| col | type | comments |
|-----|----------------------|----------------------------------|
| ti_id | bigint |  |
| ni_id | bigint | node instance id, 手动执行时为空 |
| task_id | bigint |  |
| start_time | timestamp |  |
| end_time | timestamp |  |
| exec_status | varchar | task 执行状态，RUNNING;SUCCESS;FAILED |
| error_msg | varchar | 失败信息 |
| create_time | timestamp |  |
| create_user | varchar |  |
| update_time | timestamp |  |
| update_user | varchar |  |