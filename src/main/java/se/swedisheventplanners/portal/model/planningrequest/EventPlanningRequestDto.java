package se.swedisheventplanners.portal.model.planningrequest;

import lombok.Data;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.EventRequestPlanningPriority;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EventPlanningRequestDto implements Serializable {

    private Long id;

    private String clientRecordNumber;

    private String clientName;

    private String eventType;

    private String description;

    private LocalDate eventStart;

    private LocalDate eventEnd;

    private int numberOfAttendants;

    private Double plannedBudget;

    private String decorations;

    private String foodAndDrinks;

    private String music;

    private String photos;

    private String posters;

    private String computerRelated;

    private String otherNeeds;

    private EventPlanningRequestStatus status;

    private EventRequestPlanningPriority priority;

    private String requestedBy;

    private Role assignedToRole;

    private String budgetDetails;

}
