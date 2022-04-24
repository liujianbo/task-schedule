package org.lioujianbo.taskschedule.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task_node")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskNode extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nodeId;
    private Integer flowId;
    private Integer flowVersion;
    private String taskIds;
    private String nodeType;// START;TASK;END
    private Integer prevNode;
}
