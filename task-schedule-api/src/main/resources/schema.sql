create table task_flow (
    flow_id bigint auto_increment,
    flow_name varchar(255),
    flow_description varchar(255),
    flow_version bigint,
    create_time timestamp,
    create_user varchar(50),
    update_time timestamp,
    update_user varchar(50),
    primary key (flow_id)
);

create table task_node (
    node_id bigint auto_increment,
    flow_id bigint,
    flow_version bigint,
    task_ids varchar(50),
    node_type varchar(50),
    prev_node bigint,
    create_time timestamp,
    create_user varchar(50),
    update_time timestamp,
    update_user varchar(50),
    primary key (node_id)
);

create table task (
    task_id bigint auto_increment,
    task_name varchar(255),
    task_description varchar(255),
    task_type varchar(50),
    task_config varchar(255),
    task_content varchar(255),
    create_time timestamp,
    create_user varchar(50),
    update_time timestamp,
    update_user varchar(50),
    primary key (task_id)
);

create table task_ins (
    ti_id bigint auto_increment,
    ni_id bigint,
    task_id bigint,
    start_time timestamp,
    end_time timestamp,
    exec_status varchar(50),
    error_msg varchar(255),
    create_time timestamp,
    create_user varchar(50),
    update_time timestamp,
    update_user varchar(50),
    primary key (ti_id)
);
