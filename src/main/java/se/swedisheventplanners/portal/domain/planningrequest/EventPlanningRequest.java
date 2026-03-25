package se.swedisheventplanners.portal.domain.planningrequest;

import jakarta.persistence.*;
import lombok.Data;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "event_planning_request")
public class EventPlanningRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_record_number")
    private String clientRecordNumber;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "description")
    private String description;

    @Column(name = "event_start")
    private LocalDate eventStart;

    @Column(name = "event_end")
    private LocalDate eventEnd;

    @Column(name = "number_of_attendants")
    private int numberOfAttendants;

    @Column(name = "planned_budget")
    private Double plannedBudget;

    @Column(name = "decorations")
    private String decorations;

    @Column(name = "food_and_drinks")
    private String foodAndDrinks;

    @Column(name = "music")
    private String music;

    @Column(name = "photos")
    private String photos;

    @Column(name = "posters")
    private String posters;

    @Column(name = "computer_related")
    private String computerRelated;

    @Column(name = "other_needs")
    private String otherNeeds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventPlanningRequestStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "requested_by")
    private String requestedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_to_role")
    private Role assignedToRole;

    @Column(name = "budget_details")
    private String budgetDetails;

}
