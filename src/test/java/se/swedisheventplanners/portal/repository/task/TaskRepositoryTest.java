package se.swedisheventplanners.portal.repository.task;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import se.swedisheventplanners.portal.domain.task.Task;
import se.swedisheventplanners.portal.domain.task.TaskPriority;
import se.swedisheventplanners.portal.domain.task.TaskStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

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
    public void testSave() {
        taskRepository.save(task);
        Assertions.assertThat(task.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindById() {
        Optional<Task> task1 = taskRepository.findById(ID);
        assertTrue(task1.isPresent());
    }

    @Test
    public void testFindByAssigneeId() {
        List<Task> tasks = taskRepository.findByAssigneeId(ID);
        assertFalse(tasks.isEmpty());
        assertEquals(ID, tasks.get(0).getAssigneeId());
    }

}
