# task-schedule
LiveRamp Cast Test 

# core function
1. task schedule
2. task flow execute
3. task flow monitor
4. distribute deploy

# module
REST API
1. create/update/delete task flow
2. create/update/delete task
3. task flow monitor
4. task flow execute

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