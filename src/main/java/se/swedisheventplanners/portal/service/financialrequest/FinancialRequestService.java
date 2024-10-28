package se.swedisheventplanners.portal.service.financialrequest;

import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;

import java.util.List;

public interface FinancialRequestService {

    FinancialRequest save(FinancialRequest financialRequest);

    FinancialRequest findById(Long id);

    List<FinancialRequest> findByAssignedToRole(Role role);

    FinancialRequest changePriority(Long id, Priority priority);

    FinancialRequest sendRequestToFinancialManager(Long id);

    void deleteRequest(Long id);

    FinancialRequest editRequest(Long id, FinancialRequest financialRequest);

    FinancialRequest approveRequest(Long id);

    FinancialRequest rejectRequest(Long id);
}
