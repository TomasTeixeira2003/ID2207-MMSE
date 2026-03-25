package se.swedisheventplanners.portal.repository.recruitmentrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.user.Role;

import java.util.List;

@Repository
public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Long> {

    List<RecruitmentRequest> findByAssignedToRole(Role role);

}
