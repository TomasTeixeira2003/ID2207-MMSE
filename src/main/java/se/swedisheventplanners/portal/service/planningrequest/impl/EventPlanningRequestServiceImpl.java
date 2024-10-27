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

import java.util.List;

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

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public EventPlanningRequest findById(Long id) {
        return eventPlanningRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No event planning request found with id: " + id));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<EventPlanningRequest> findByAssignedToRole(Role role) {
        return eventPlanningRequestRepository.findByAssignedToRole(role);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest sendRequest(Long id, Role assignedToRole) {
        EventPlanningRequest eventPlanningRequest = findById(id);
        eventPlanningRequest.setAssignedToRole(assignedToRole);
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }
}
