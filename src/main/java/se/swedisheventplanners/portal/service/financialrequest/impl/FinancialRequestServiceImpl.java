package se.swedisheventplanners.portal.service.financialrequest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.financialrequest.FinancialRequestRepository;
import se.swedisheventplanners.portal.service.sepuser.SepUserService;
import se.swedisheventplanners.portal.service.financialrequest.FinancialRequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRequestServiceImpl implements FinancialRequestService {

    private final FinancialRequestRepository financialRequestRepository;
    private final SepUserService sepUserService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FinancialRequest save(FinancialRequest financialRequest) {
        financialRequest.setStatus(FinancialRequestStatus.OPEN);
        financialRequest.setPriority(Priority.LOW);
        return financialRequestRepository.save(financialRequest);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public FinancialRequest findById(Long id) {
        return financialRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("FinancialRequest not found"));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<FinancialRequest> findByAssignedToRole(Role role) {
        return financialRequestRepository.findByAssignedToRole(role);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FinancialRequest changePriority(Long id, Priority priority) {
        FinancialRequest financialRequest = findById(id);
        financialRequest.setPriority(priority);
        return financialRequest;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FinancialRequest sendRequestToFinancialManager(Long id) {
        FinancialRequest financialRequest = findById(id);
        financialRequest.setAssignedToRole(Role.FINANCIAL_MANAGER);
        return financialRequest;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteRequest(Long id) {
        financialRequestRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FinancialRequest editRequest(Long id, FinancialRequest financialRequest) {
        FinancialRequest originalFinancialRequest = findById(id);
        financialRequest.setId(originalFinancialRequest.getId());
        financialRequest.setStatus(originalFinancialRequest.getStatus());
        financialRequest.setPriority(originalFinancialRequest.getPriority());
        financialRequest.setCreatedBy(originalFinancialRequest.getCreatedBy());
        financialRequest.setAssignedToRole(originalFinancialRequest.getAssignedToRole());
        return financialRequestRepository.save(financialRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FinancialRequest approveRequest(Long id) {
        FinancialRequest financialRequest = findById(id);
        SepUser creator = sepUserService.findByUsername(financialRequest.getCreatedBy());
        financialRequest.setAssignedToRole(creator.getRole());
        financialRequest.setStatus(FinancialRequestStatus.APPROVED);
        return financialRequest;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FinancialRequest rejectRequest(Long id) {
        FinancialRequest financialRequest = findById(id);
        SepUser creator = sepUserService.findByUsername(financialRequest.getCreatedBy());
        financialRequest.setAssignedToRole(creator.getRole());
        financialRequest.setStatus(FinancialRequestStatus.REJECTED);
        return financialRequest;
    }
}
