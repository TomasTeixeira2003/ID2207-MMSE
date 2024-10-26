package se.swedisheventplanners.portal.service.task.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.task.Task;
import se.swedisheventplanners.portal.repository.task.TaskRepository;
import se.swedisheventplanners.portal.service.task.TaskService;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
