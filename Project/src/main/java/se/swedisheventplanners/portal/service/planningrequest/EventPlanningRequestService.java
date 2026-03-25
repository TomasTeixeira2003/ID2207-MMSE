package se.swedisheventplanners.portal.service.planningrequest;


import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequest;
import se.swedisheventplanners.portal.domain.user.Role;

import java.util.List;

public interface EventPlanningRequestService {

    EventPlanningRequest save(EventPlanningRequest eventPlanningRequest);

    EventPlanningRequest findById(Long id);

    List<EventPlanningRequest> findByAssignedToRole(Role role);

    EventPlanningRequest sendRequest(Long id, Role assignedToRole);

    EventPlanningRequest rejectRequest(Long id);

    EventPlanningRequest approveRequest(Long id);

    EventPlanningRequest closeRequest(Long id);

    EventPlanningRequest archiveRequest(Long id);

    EventPlanningRequest editRequest(Long id, EventPlanningRequest eventPlanningRequest);

}
