package se.swedisheventplanners.portal.service.recruitmentrequest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequestStatus;
import se.swedisheventplanners.portal.repository.recruitmentrequest.RecruitmentRequestRepository;
import se.swedisheventplanners.portal.service.recruitmentrequest.RecruitmentRequestService;

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
}
