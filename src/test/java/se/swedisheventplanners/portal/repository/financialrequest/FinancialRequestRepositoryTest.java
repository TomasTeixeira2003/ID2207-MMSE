package se.swedisheventplanners.portal.repository.financialrequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
public class FinancialRequestRepositoryTest {

    @Autowired
    private FinancialRequestRepository financialRequestRepository;

    private FinancialRequest financialRequest;
    private SepUser sepUser;

    private static final Long ID = 1L;
    private static final String USERNAME = "bakalis";
    private static final String PROJECT = "KTH";
    private static final String REASON = "Replacing the Doors";
    private static final String AMOUNT = "5000";

    @BeforeEach
    public void setUp() {
        financialRequest = new FinancialRequest();
        financialRequest.setStatus(FinancialRequestStatus.APPROVED);
        financialRequest.setPriority(Priority.MEDIUM);
        financialRequest.setAssignedToRole(Role.PRODUCTION_MANAGER);
        financialRequest.setCreatedBy(USERNAME);
        financialRequest.setRequestingDepartment(RequestingDepartment.PRODUCTION);
        financialRequest.setProjectReference(PROJECT);
        financialRequest.setReason(REASON);
        financialRequest.setRequiredAmount(AMOUNT);
        sepUser = new SepUser();
        sepUser.setRole(Role.PRODUCTION_MANAGER);
        sepUser.setActive(true);
        sepUser.setUsername(USERNAME);
    }

    @Test
    public void testSave() {
        financialRequestRepository.save(financialRequest);
        Assertions.assertThat(financialRequest.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindById() {
        Optional<FinancialRequest> financialRequest1 = financialRequestRepository.findById(ID);
        assertTrue(financialRequest1.isPresent());
    }

    @Test
    public void testFindByAssignedToRole() {
        List<FinancialRequest> financialRequests = financialRequestRepository.findByAssignedToRole(Role.PRODUCTION_MANAGER);
        assertFalse(financialRequests.isEmpty());
        assertEquals(Role.PRODUCTION_MANAGER, financialRequests.get(0).getAssignedToRole());
    }

}
