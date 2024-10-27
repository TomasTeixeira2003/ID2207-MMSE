package se.swedisheventplanners.portal.service.sepuser.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.user.SepUserRepository;
import se.swedisheventplanners.portal.service.SepUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SepUserServiceImpl implements SepUserService {

    private final SepUserRepository sepUserRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SepUser createSepUser(SepUser sepUser) {
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
    public List<SepUser> deactivateUser(Long id) {
        SepUser sepUser = sepUserRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("User with id: %d not found", id)));
        sepUser.setActive(false);
        sepUserRepository.save(sepUser);
        return sepUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<SepUser> reactivateUser(Long id) {
        SepUser sepUser = sepUserRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("User with id: %d not found", id)));
        sepUser.setActive(true);
        sepUserRepository.save(sepUser);
        return sepUserRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<SepUser> deleteUser(Long id) {
        sepUserRepository.deleteById(id);
        return sepUserRepository.findAll();
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
        return sepUserRepository.save(user);
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
