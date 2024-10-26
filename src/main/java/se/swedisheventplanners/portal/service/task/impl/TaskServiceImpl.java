package se.swedisheventplanners.portal.service.task.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.task.Priority;
import se.swedisheventplanners.portal.domain.task.Task;
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
        return taskRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Task with id: %s not found", id)));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> deleteTask(Long id) {
        taskRepository.deleteById(id);
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> changeTaskPriority(Long id, Priority priority) {
        Task task = findById(id);
        task.setPriority(priority);
        taskRepository.save(task);
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> changeTaskAssignee(Long id, Long assigneeId) {
        Task task = findById(id);
        task.setAssigneeId(assigneeId);
        taskRepository.save(task);
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task edit(Task editedTask) {
        Task originalTask = findById(editedTask.getId());
        editedTask.setStatus(originalTask.getStatus());
        return taskRepository.save(editedTask);
    }
}
