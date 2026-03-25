package se.swedisheventplanners.portal.repository.financialrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.user.Role;

import java.util.List;

@Repository
public interface FinancialRequestRepository extends JpaRepository<FinancialRequest, Long> {

    List<FinancialRequest> findByAssignedToRole(Role role);
}
