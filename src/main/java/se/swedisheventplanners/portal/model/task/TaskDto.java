package se.swedisheventplanners.portal.model.task;

import lombok.Data;
import se.swedisheventplanners.portal.domain.task.Priority;

import java.io.Serializable;

@Data
public class TaskDto implements Serializable {

    Long id;
    String project;
    String description;
    Long assigneeId;
    Priority priority;

}
