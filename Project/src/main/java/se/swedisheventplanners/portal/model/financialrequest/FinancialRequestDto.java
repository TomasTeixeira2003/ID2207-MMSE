package se.swedisheventplanners.portal.model.financialrequest;

import lombok.Data;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequestStatus;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;

@Data
public class FinancialRequestDto implements Serializable {

    private Long id;

    private RequestingDepartment requestingDepartment;

    private String projectReference;

    private String requiredAmount;

    private String reason;

    private String createdBy;

    private FinancialRequestStatus status;

    private Priority priority;

    private Role assignedToRole;

}
