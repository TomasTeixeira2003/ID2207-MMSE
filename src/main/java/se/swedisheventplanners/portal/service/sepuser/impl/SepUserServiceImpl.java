package se.swedisheventplanners.portal.service.sepuser.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.SepUserRepository;
import se.swedisheventplanners.portal.service.SepUserService;

@Service
@RequiredArgsConstructor
public class SepUserServiceImpl implements SepUserService {

    private final SepUserRepository sepUserRepository;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SepUser createSepUser(SepUser sepUser) {
        return sepUserRepository.save(sepUser);
    }

}
