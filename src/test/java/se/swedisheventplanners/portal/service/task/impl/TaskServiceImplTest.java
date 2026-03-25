package se.swedisheventplanners.portal.service.task.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.swedisheventplanners.portal.domain.task.Task;
import se.swedisheventplanners.portal.domain.task.TaskPriority;
import se.swedisheventplanners.portal.domain.task.TaskStatus;
import se.swedisheventplanners.portal.repository.task.TaskRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    private static final Long ID = 1L;
    private static final String USERNAME = "bakalis";
    private static final String PROJECT = "KTH";
    private static final String REASON = "Replacing the Doors";

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(ID);
        task.setStatus(TaskStatus.COMPLETED);
        task.setPriority(TaskPriority.CRITICAL);
        task.setAssigneeId(ID);
        task.setProject(PROJECT);
        task.setDescription(REASON);
    }

    @Test
    void save() {
        Mockito.when(repository.save(task)).thenReturn(task);
        taskService.save(task);
        Mockito.verify(repository).save(task);
        assertEquals(TaskStatus.ASSIGNED, task.getStatus());
    }

    @Test
    void changeTaskPriority() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(task));
        TaskPriority newPriority = TaskPriority.BLOCKER;
        Task modifiedTask = taskService.changeTaskPriority(ID, newPriority);
        assertEquals(newPriority, modifiedTask.getPriority());
    }

    @Test
    void changeTaskAssignee() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(task));
        Long newAssigneeId = 2L;
        Task modifiedTask = taskService.changeTaskAssignee(ID, newAssigneeId);
        assertEquals(newAssigneeId, modifiedTask.getAssigneeId());
    }

    @Test
    void changeTaskStatus() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(task));
        TaskStatus newStatus = TaskStatus.COMPLETED;
        Task modifiedTask = taskService.changeTaskStatus(ID, newStatus);
        assertEquals(newStatus, modifiedTask.getStatus());
    }
}