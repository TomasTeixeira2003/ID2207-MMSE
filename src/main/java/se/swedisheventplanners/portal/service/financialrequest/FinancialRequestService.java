package se.swedisheventplanners.portal.service.financialrequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;

import java.util.List;

public interface FinancialRequestService {
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    FinancialRequest save(FinancialRequest financialRequest);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    FinancialRequest findById(Long id);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    List<FinancialRequest> findByAssignedToRole(Role role);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    FinancialRequest changePriority(Long id, Priority priority);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    FinancialRequest sendRequestToFinancialManager(Long id);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    void deleteRequest(Long id);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    FinancialRequest editRequest(Long id, FinancialRequest financialRequest);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    FinancialRequest approveRequest(Long id);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    FinancialRequest rejectRequest(Long id);
}
