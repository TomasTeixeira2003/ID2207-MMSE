package se.swedisheventplanners.portal.service.sepuser.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.domain.user.SepUserDetails;
import se.swedisheventplanners.portal.repository.SepUserRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SepUserDetailsService implements UserDetailsService {

    private final SepUserRepository userRepository;

    public SepUserDetailsService(final SepUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SepUserDetails loadUserByUsername(final String username) {
        final SepUser user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        if (!user.isActive()) {
            log.warn("user not active: {}", username);
            throw new IllegalArgumentException("User " + username + " not active");
        }
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        return new SepUserDetails(user.getId(), username, user.getHash(), roles);
    }

}
