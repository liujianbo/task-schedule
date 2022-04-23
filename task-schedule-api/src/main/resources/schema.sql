drop table task_flow if exists;
drop table task_node if exists;
drop table task if exists;

create table task_flow (
    flow_id bigint auto_increment,
    flow_name varchar(255),
    flow_description varchar(255),
    create_time timestamp,
    create_user varchar(50),
    update_time timestamp,
    update_user varchar(50),
    primary key (flow_id)
);

create table task_node (
    node_id bigint auto_increment,
    task_id bigint,
    flow_id bigint,
    node_type varchar(50),
    prev_node varchar(50),
    next_node varchar(50),
    create_time timestamp,
    create_user varchar(50),
    update_time timestamp,
    update_user varchar(50),
    primary key (node_id)
);

create table task (
    task_id bigint not null,
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
