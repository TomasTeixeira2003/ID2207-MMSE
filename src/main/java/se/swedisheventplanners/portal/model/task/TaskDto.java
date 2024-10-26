package se.swedisheventplanners.portal.model.task;

import lombok.Data;
import se.swedisheventplanners.portal.domain.task.Priority;
import se.swedisheventplanners.portal.domain.task.TaskStatus;

import java.io.Serializable;

@Data
public class TaskDto implements Serializable {

    Long id;
    String project;
    String description;
    TaskStatus status;
    Long assigneeId;
    String assigneeName;
    Priority priority;

}
