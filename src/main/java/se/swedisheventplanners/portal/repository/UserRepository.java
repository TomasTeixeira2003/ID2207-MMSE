package se.swedisheventplanners.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.swedisheventplanners.portal.domain.user.SepUser;

public interface UserRepository extends JpaRepository<SepUser, Long> {

    SepUser findByUsernameIgnoreCase(String username);

}
