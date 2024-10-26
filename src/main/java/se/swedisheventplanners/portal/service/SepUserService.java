package se.swedisheventplanners.portal.service;

import se.swedisheventplanners.portal.domain.user.SepUser;

import java.util.List;

public interface SepUserService {

    SepUser createSepUser(SepUser sepUser);

    List<SepUser> findAll();

    List<SepUser> deactivateUser(Long id);

    List<SepUser> reactivateUser(Long id);

    List<SepUser> deleteUser(Long id);
}
