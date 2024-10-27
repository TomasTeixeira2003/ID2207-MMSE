package se.swedisheventplanners.portal.service.planningrequest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequest;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.EventRequestPlanningPriority;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.repository.planningrequest.EventPlanningRequestRepository;
import se.swedisheventplanners.portal.service.planningrequest.EventPlanningRequestService;

@Service
@RequiredArgsConstructor
public class EventPlanningRequestServiceImpl implements EventPlanningRequestService {

    private final EventPlanningRequestRepository eventPlanningRequestRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest save(EventPlanningRequest eventPlanningRequest) {
        eventPlanningRequest.setStatus(EventPlanningRequestStatus.PENDING);
        eventPlanningRequest.setPriority(EventRequestPlanningPriority.LOW);
        eventPlanningRequest.setAssignedToRole(Role.CUSTOMER_SUPPORT_OFFICER);
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }
}
