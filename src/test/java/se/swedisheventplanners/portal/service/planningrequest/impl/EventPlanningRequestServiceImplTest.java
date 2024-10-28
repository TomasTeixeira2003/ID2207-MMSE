package se.swedisheventplanners.portal.service.planningrequest.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequest;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.repository.planningrequest.EventPlanningRequestRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventPlanningRequestServiceImplTest {

    @Mock
    private EventPlanningRequestRepository repository;

    @InjectMocks
    private EventPlanningRequestServiceImpl eventPlanningRequestService;

    private static final Long ID = 1L;
    private static final String USERNAME = "bakalis";
    private static final String AMOUNT = "5000";

    private EventPlanningRequest eventPlanningRequest;

    @BeforeEach
    public void setUp() {
        eventPlanningRequest = new EventPlanningRequest();
        eventPlanningRequest.setId(ID);
        eventPlanningRequest.setStatus(EventPlanningRequestStatus.ARCHIVED);
        eventPlanningRequest.setPriority(Priority.MEDIUM);
        eventPlanningRequest.setAssignedToRole(Role.CUSTOMER_SUPPORT_OFFICER);
        eventPlanningRequest.setRequestedBy(USERNAME);
        eventPlanningRequest.setBudgetDetails(AMOUNT);
        eventPlanningRequest.setEventStart(LocalDate.now());
        eventPlanningRequest.setEventEnd(LocalDate.now().plusDays(5));
    }


    @Test
    void save() {
        Mockito.when(repository.save(eventPlanningRequest)).thenReturn(eventPlanningRequest);
        eventPlanningRequestService.save(eventPlanningRequest);
        assertEquals(Priority.LOW, eventPlanningRequest.getPriority());
        assertEquals(EventPlanningRequestStatus.PENDING, eventPlanningRequest.getStatus());
    }

    @Test
    void sendRequest() {
        Role sendToRole = Role.SENIOR_CUSTOMER_SUPPORT_OFFICER;
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(eventPlanningRequest));
        EventPlanningRequest modifiedRequest = eventPlanningRequestService.sendRequest(ID, sendToRole);
        assertEquals(sendToRole, modifiedRequest.getAssignedToRole());
    }

    @Test
    void rejectRequest() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(eventPlanningRequest));
        EventPlanningRequest modifiedRequest = eventPlanningRequestService.rejectRequest(ID);
        assertEquals(EventPlanningRequestStatus.REJECTED, modifiedRequest.getStatus());
    }

    @Test
    void approveRequest() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(eventPlanningRequest));
        EventPlanningRequest modifiedRequest = eventPlanningRequestService.approveRequest(ID);
        assertEquals(EventPlanningRequestStatus.IN_PROGRESS, modifiedRequest.getStatus());
    }

    @Test
    void closeRequest() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(eventPlanningRequest));
        EventPlanningRequest modifiedRequest = eventPlanningRequestService.closeRequest(ID);
        assertEquals(EventPlanningRequestStatus.CLOSED, modifiedRequest.getStatus());
    }

    @Test
    void archiveRequest() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(eventPlanningRequest));
        EventPlanningRequest modifiedRequest = eventPlanningRequestService.archiveRequest(ID);
        assertEquals(EventPlanningRequestStatus.ARCHIVED, modifiedRequest.getStatus());
    }
}