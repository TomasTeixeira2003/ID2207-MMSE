package se.swedisheventplanners.portal.service.recruitmentrequest.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequestStatus;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.recruitmentrequest.RecruitmentRequestRepository;
import se.swedisheventplanners.portal.service.user.SepUserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RecruitmentRequestServiceImplTest {

    @Mock
    private RecruitmentRequestRepository recruitmentRequestRepository;

    @Mock
    private SepUserService sepUserService;

    @InjectMocks
    private RecruitmentRequestServiceImpl recruitmentRequestService;

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
    void save() {
        Mockito.when(recruitmentRequestRepository.save(recruitmentRequest)).thenReturn(recruitmentRequest);
        recruitmentRequestService.save(recruitmentRequest);
        assertEquals(RecruitmentRequestStatus.DRAFT, recruitmentRequest.getStatus());
        assertEquals(Priority.LOW, recruitmentRequest.getPriority());
    }

    @Test
    void changePriority() {
        Mockito.when(recruitmentRequestRepository.findById(ID)).thenReturn(Optional.of(recruitmentRequest));
        Priority changedPriority = Priority.MEDIUM;
        RecruitmentRequest modifiedRequest = recruitmentRequestService.changePriority(ID, changedPriority);
        assertEquals(changedPriority, modifiedRequest.getPriority());
    }

    @Test
    void sendRequestToHR() {
        Mockito.when(recruitmentRequestRepository.findById(ID)).thenReturn(Optional.of(recruitmentRequest));
        RecruitmentRequest modifiedRequest = recruitmentRequestService.sendRequestToHR(ID);
        assertEquals(Role.HR_MANAGER, modifiedRequest.getAssignedToRole());
    }

    @Test
    void closeRequest() {
        Mockito.when(recruitmentRequestRepository.findById(ID)).thenReturn(Optional.of(recruitmentRequest));
        Mockito.when(sepUserService.findByUsername(USERNAME)).thenReturn(sepUser);
        RecruitmentRequest modifiedRequest = recruitmentRequestService.closeRequest(ID);
        assertEquals(RecruitmentRequestStatus.FULFILLED, modifiedRequest.getStatus());
    }
}