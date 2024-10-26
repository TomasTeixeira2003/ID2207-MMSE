package se.swedisheventplanners.portal.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;

import java.util.List;

public interface SepUserRepository extends JpaRepository<SepUser, Long> {

    SepUser findByUsernameIgnoreCase(String username);

    List<SepUser> findByRole(Role subTeamRole);
}
