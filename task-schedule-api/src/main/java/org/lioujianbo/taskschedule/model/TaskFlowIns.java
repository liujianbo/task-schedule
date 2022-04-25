package org.lioujianbo.taskschedule.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task_flow_ins")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskFlowIns extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fiId;
    private Integer flowId;
    private Date startTime;
    private Date endTime;
    private String status;// RUNNING;SUCCESS;FAILED
    private String errorMsg;
}
