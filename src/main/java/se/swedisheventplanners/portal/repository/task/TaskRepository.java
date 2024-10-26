package se.swedisheventplanners.portal.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.swedisheventplanners.portal.domain.task.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
