package se.swedisheventplanners.portal.service.financialrequest.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.financialrequest.FinancialRequestRepository;
import se.swedisheventplanners.portal.service.user.SepUserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class FinancialRequestServiceImplTest {

    @Mock
    private FinancialRequestRepository repository;

    @Mock
    private SepUserService sepUserService;

    @InjectMocks
    private FinancialRequestServiceImpl financialRequestService;

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
        financialRequest.setId(ID);
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
    void save() {
        Mockito.when(repository.save(financialRequest)).thenReturn(financialRequest);
        financialRequestService.save(financialRequest);
        Mockito.verify(repository, Mockito.times(1)).save(financialRequest);
        assertEquals(Priority.LOW, financialRequest.getPriority());
        assertEquals(FinancialRequestStatus.OPEN, financialRequest.getStatus());
    }

    @Test
    void changePriority() {
        Priority newPriority = Priority.MEDIUM;
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(financialRequest));
        FinancialRequest modifiedRequest = financialRequestService.changePriority(ID, newPriority);
        assertEquals(newPriority, modifiedRequest.getPriority());
    }

    @Test
    void sendRequestToFinancialManager() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(financialRequest));
        FinancialRequest modifiedRequest = financialRequestService.sendRequestToFinancialManager(ID);
        assertEquals(Role.FINANCIAL_MANAGER, modifiedRequest.getAssignedToRole());
    }

    @Test
    void approveRequest() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(financialRequest));
        Mockito.when(sepUserService.findByUsername(USERNAME)).thenReturn(sepUser);
        FinancialRequest modifiedRequest = financialRequestService.approveRequest(ID);
        assertEquals(FinancialRequestStatus.APPROVED, modifiedRequest.getStatus());
    }

    @Test
    void rejectRequest() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(financialRequest));
        Mockito.when(sepUserService.findByUsername(USERNAME)).thenReturn(sepUser);
        FinancialRequest modifiedRequest = financialRequestService.rejectRequest(ID);
        assertEquals(FinancialRequestStatus.REJECTED, modifiedRequest.getStatus());
    }
}