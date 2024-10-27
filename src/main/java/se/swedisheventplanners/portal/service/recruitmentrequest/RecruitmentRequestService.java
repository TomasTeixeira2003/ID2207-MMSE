package se.swedisheventplanners.portal.service.recruitmentrequest;

import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.user.Role;

import java.util.List;

public interface RecruitmentRequestService {

    RecruitmentRequest save(RecruitmentRequest recruitmentRequest);

    RecruitmentRequest findById(Long id);

    List<RecruitmentRequest> findByAssignedToRole(Role role);

    RecruitmentRequest changePriority(Long id, Priority priority);

    RecruitmentRequest sendRequestToHR(Long id);

    void deleteRequest(Long id);
}
