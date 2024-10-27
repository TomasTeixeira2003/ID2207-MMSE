package se.swedisheventplanners.portal.repository.planningrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequest;

@Repository
public interface EventPlanningRequestRepository extends JpaRepository<EventPlanningRequest, Long> {
}
