package se.swedisheventplanners.portal.service.sepuser;

import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;

import java.util.List;

public interface SepUserService {

    SepUser createSepUser(SepUser sepUser);

    List<SepUser> findAll();

    List<SepUser> deactivateUser(Long id);

    List<SepUser> reactivateUser(Long id);

    List<SepUser> deleteUser(Long id);

    SepUser findById(Long id);

    SepUser editUserRole(Long id, Role role);

    List<SepUser> findByRole(Role subTeamRole);

    SepUser findByUsername(String name);
}
