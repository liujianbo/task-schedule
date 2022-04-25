package org.lioujianbo.taskschedule.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task_node_ins")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskNodeIns extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer niId;
    private Integer fiId;
    private Integer nodeId;
    private Date startTime;
    private Date endTime;
    private String status;// RUNNING;SUCCESS;FAILED
    private String errorMsg;
}
