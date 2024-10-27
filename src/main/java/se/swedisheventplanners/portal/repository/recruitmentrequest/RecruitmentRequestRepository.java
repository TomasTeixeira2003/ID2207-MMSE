package se.swedisheventplanners.portal.repository.recruitmentrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;

@Repository
public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {
}
