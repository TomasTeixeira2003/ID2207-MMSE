package se.swedisheventplanners.portal.repository.recruitmentrequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequestStatus;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
public class RecruitmentRequestRepositoryTest {

    @Autowired
    private RecruitmentRequestRepository recruitmentRequestRepository;

    private RecruitmentRequest recruitmentRequest;
    private SepUser sepUser;

    private static final Long ID = 1L;
    private static final String USERNAME = "bakalis";

    @BeforeEach
    public void setUp() {
        recruitmentRequest = new RecruitmentRequest();
        recruitmentRequest.setId(ID);
        recruitmentRequest.setStatus(RecruitmentRequestStatus.FULFILLED);
        recruitmentRequest.setPriority(Priority.MEDIUM);
        recruitmentRequest.setAssignedToRole(Role.PRODUCTION_MANAGER);
        recruitmentRequest.setCreatedBy(USERNAME);
        recruitmentRequest.setRequestingDepartment(RequestingDepartment.PRODUCTION);
        sepUser = new SepUser();
        sepUser.setRole(Role.PRODUCTION_MANAGER);
        sepUser.setActive(true);
        sepUser.setUsername(USERNAME);
    }

    @Test
    public void testSave() {
        recruitmentRequestRepository.save(recruitmentRequest);
        Assertions.assertThat(recruitmentRequest.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindById() {
        Optional<RecruitmentRequest> recruitmentRequest1 = recruitmentRequestRepository.findById(ID);
        assertTrue(recruitmentRequest1.isPresent());
    }

    @Test
    public void testFindByAssignedToRole() {
        List<RecruitmentRequest> recruitmentRequests = recruitmentRequestRepository.findByAssignedToRole(Role.PRODUCTION_MANAGER);
        assertFalse(recruitmentRequests.isEmpty());
        assertEquals(Role.PRODUCTION_MANAGER, recruitmentRequests.get(0).getAssignedToRole());
    }

}
