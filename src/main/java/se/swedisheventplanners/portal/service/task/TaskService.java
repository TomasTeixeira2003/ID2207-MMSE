package se.swedisheventplanners.portal.service.task;

import se.swedisheventplanners.portal.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task save(Task task);

    List<Task> findAll();

    List<Task> deleteTask(Long id);

}
