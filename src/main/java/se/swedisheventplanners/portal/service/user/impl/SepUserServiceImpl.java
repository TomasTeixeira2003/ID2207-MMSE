package se.swedisheventplanners.portal.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.user.SepUserRepository;
import se.swedisheventplanners.portal.service.user.SepUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SepUserServiceImpl implements SepUserService {

    private final SepUserRepository sepUserRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SepUser createSepUser(SepUser sepUser) {
        SepUser foundUser = findByUsername(sepUser.getUsername());
        if (foundUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        sepUser.setActive(true);
        return sepUserRepository.save(sepUser);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<SepUser> findAll() {
        return sepUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SepUser deactivateUser(Long id) {
        SepUser sepUser = findById(id);
        sepUser.setActive(false);
        return sepUser;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SepUser reactivateUser(Long id) {
        SepUser sepUser = findById(id);
        sepUser.setActive(true);
        return sepUser;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteUser(Long id) {
        sepUserRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public SepUser findById(Long id) {
        return sepUserRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("User with id: %d not found", id)));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SepUser editUserRole(Long id, Role role) {
        SepUser user = findById(id);
        user.setRole(role);
        return user;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<SepUser> findByRole(Role subTeamRole) {
        return sepUserRepository.findByRole(subTeamRole);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public SepUser findByUsername(String name) {
        return sepUserRepository.findByUsernameIgnoreCase(name);
    }

}
