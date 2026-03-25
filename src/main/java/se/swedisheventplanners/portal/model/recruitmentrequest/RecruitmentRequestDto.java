package se.swedisheventplanners.portal.model.recruitmentrequest;

import lombok.Data;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.ContractType;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequestStatus;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RequestingDepartment;
import se.swedisheventplanners.portal.domain.user.Role;

import java.io.Serializable;

@Data
public class RecruitmentRequestDto implements Serializable {

    private Long id;

    private ContractType contractType;

    private RequestingDepartment requestingDepartment;

    private String experience;

    private String jobTitle;

    private String jobDescription;

    private String createdBy;

    private RecruitmentRequestStatus status;

    private Priority priority;

    private Role assignedToRole;

}
