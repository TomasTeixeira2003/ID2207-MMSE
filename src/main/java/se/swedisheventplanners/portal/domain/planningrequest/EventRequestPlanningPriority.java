package se.swedisheventplanners.portal.domain.planningrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventRequestPlanningPriority {

    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String label;
}
