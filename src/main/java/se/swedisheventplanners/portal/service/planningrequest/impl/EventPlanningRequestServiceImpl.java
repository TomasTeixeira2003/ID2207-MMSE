package se.swedisheventplanners.portal.service.planningrequest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest rejectRequest(Long id) {
        EventPlanningRequest eventPlanningRequest = findById(id);
        eventPlanningRequest.setStatus(EventPlanningRequestStatus.REJECTED);
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest approveRequest(Long id) {
        EventPlanningRequest eventPlanningRequest = findById(id);
        eventPlanningRequest.setStatus(EventPlanningRequestStatus.IN_PROGRESS);
        eventPlanningRequest.setAssignedToRole(Role.SENIOR_CUSTOMER_SUPPORT_OFFICER);
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest closeRequest(Long id) {
        EventPlanningRequest eventPlanningRequest = findById(id);
        eventPlanningRequest.setStatus(EventPlanningRequestStatus.CLOSED);
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest archiveRequest(Long id) {
        EventPlanningRequest eventPlanningRequest = findById(id);
        eventPlanningRequest.setStatus(EventPlanningRequestStatus.ARCHIVED);
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EventPlanningRequest editRequest(Long id, EventPlanningRequest eventPlanningRequest) {
        EventPlanningRequest originalRequest = findById(id);
        eventPlanningRequest.setId(id);
        eventPlanningRequest.setStatus(originalRequest.getStatus());
        eventPlanningRequest.setRequestedBy(originalRequest.getRequestedBy());
        eventPlanningRequest.setPriority(originalRequest.getPriority());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        if (!Role.FINANCIAL_MANAGER.equals(role)) {
            eventPlanningRequest.setBudgetDetails(originalRequest.getBudgetDetails());
        }
        eventPlanningRequest.setAssignedToRole(originalRequest.getAssignedToRole());
        return eventPlanningRequestRepository.save(eventPlanningRequest);
    }
}
