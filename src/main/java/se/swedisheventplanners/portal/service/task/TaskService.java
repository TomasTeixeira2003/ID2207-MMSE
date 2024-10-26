package se.swedisheventplanners.portal.service.task;

import se.swedisheventplanners.portal.domain.task.Priority;
import se.swedisheventplanners.portal.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task save(Task task);

    List<Task> findAll();

    Task findById(Long id);

    List<Task> deleteTask(Long id);

    List<Task> changeTaskPriority(Long id, Priority priority);

    List<Task> changeTaskAssignee(Long id, Long assigneeId);

    Task edit(Task editedTask);

    List<Task> findByAssigneeId(Long assigneeId);
}
