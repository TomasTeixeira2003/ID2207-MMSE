package se.swedisheventplanners.portal.domain.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {

    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    ABORTED("Aborted");

    private String label;
}
