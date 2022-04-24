package org.lioujianbo.taskschedule.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task_ins")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskIns extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tiId;
    private Integer niId;
    private Integer taskId;
    private Date startTime;
    private Date endTime;
    private String execStatus;// RUNNING;SUCCESS;FAILED
    private String errorMsg;
}
