package se.swedisheventplanners.portal.repository.planningrequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequest;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
public class EventPlanningRequestRepositoryTest {

    @Autowired
    private EventPlanningRequestRepository eventPlanningRequestRepository;

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
    public void testSave() {
        eventPlanningRequestRepository.save(eventPlanningRequest);
        Assertions.assertThat(eventPlanningRequest.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindById() {
        Optional<EventPlanningRequest> eventPlanningRequest1 = eventPlanningRequestRepository.findById(ID);
        assertTrue(eventPlanningRequest1.isPresent());
    }

    @Test
    public void testFindByAssignedToRole() {
        List<EventPlanningRequest> eventPlanningRequests = eventPlanningRequestRepository.findByAssignedToRole(Role.CUSTOMER_SUPPORT_OFFICER);
        assertFalse(eventPlanningRequests.isEmpty());
        assertEquals(Role.CUSTOMER_SUPPORT_OFFICER, eventPlanningRequests.get(0).getAssignedToRole());
    }
}
