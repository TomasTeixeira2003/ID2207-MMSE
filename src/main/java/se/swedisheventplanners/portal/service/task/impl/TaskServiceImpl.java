package se.swedisheventplanners.portal.service.task.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.task.TaskPriority;
import se.swedisheventplanners.portal.domain.task.Task;
import se.swedisheventplanners.portal.domain.task.TaskStatus;
import se.swedisheventplanners.portal.repository.task.TaskRepository;
import se.swedisheventplanners.portal.service.task.TaskService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task save(Task task) {
        task.setStatus(TaskStatus.ASSIGNED);
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("Task with id: %s not found", id)));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task changeTaskPriority(Long id, TaskPriority priority) {
        Task task = findById(id);
        task.setPriority(priority);
        return task;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task changeTaskAssignee(Long id, Long assigneeId) {
        Task task = findById(id);
        task.setAssigneeId(assigneeId);
        return task;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task edit(Task editedTask) {
        Task originalTask = findById(editedTask.getId());
        editedTask.setStatus(originalTask.getStatus());
        return taskRepository.save(editedTask);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> findByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task changeTaskStatus(Long id, TaskStatus status) {
        Task task = findById(id);
        task.setStatus(status);
        return task;
    }
}
