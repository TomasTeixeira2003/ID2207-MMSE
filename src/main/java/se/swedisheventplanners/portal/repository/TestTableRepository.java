package se.swedisheventplanners.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.swedisheventplanners.portal.domain.event.TestTable;

@Repository
public interface TestTableRepository extends JpaRepository<TestTable, Long> {
}
