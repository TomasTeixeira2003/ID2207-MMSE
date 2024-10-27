package se.swedisheventplanners.portal.service.recruitmentrequest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequestStatus;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.repository.recruitmentrequest.RecruitmentRequestRepository;
import se.swedisheventplanners.portal.service.recruitmentrequest.RecruitmentRequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentRequestServiceImpl implements RecruitmentRequestService {

    private final RecruitmentRequestRepository recruitmentRequestRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RecruitmentRequest save(RecruitmentRequest recruitmentRequest) {
        recruitmentRequest.setStatus(RecruitmentRequestStatus.DRAFT);
        recruitmentRequest.setPriority(Priority.LOW);
        return recruitmentRequestRepository.save(recruitmentRequest);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public RecruitmentRequest findById(Long id) {
        return recruitmentRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("RecruitmentRequest not found"));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RecruitmentRequest> findByAssignedToRole(Role role) {
        return recruitmentRequestRepository.findByAssignedToRole(role);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RecruitmentRequest changePriority(Long id, Priority priority) {
        RecruitmentRequest recruitmentRequest = findById(id);
        recruitmentRequest.setPriority(priority);
        return recruitmentRequestRepository.save(recruitmentRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RecruitmentRequest sendRequestToHR(Long id) {
        RecruitmentRequest recruitmentRequest = findById(id);
        recruitmentRequest.setAssignedToRole(Role.HR_MANAGER);
        return recruitmentRequestRepository.save(recruitmentRequest);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteRequest(Long id) {
        recruitmentRequestRepository.deleteById(id);
    }
}
