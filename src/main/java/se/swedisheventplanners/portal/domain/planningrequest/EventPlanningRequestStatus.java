package se.swedisheventplanners.portal.domain.planningrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventPlanningRequestStatus {

    PENDING("Pending"),
    REJECTED("Rejected"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed"),
    ARCHIVED("Archived");

    private final String label;
}
