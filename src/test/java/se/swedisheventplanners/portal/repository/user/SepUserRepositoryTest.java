package se.swedisheventplanners.portal.repository.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
public class SepUserRepositoryTest {

    @Autowired
    private SepUserRepository sepUserRepository;

    private SepUser sepUser;
    private static final Long ID = 1L;
    private static final String USERNAME = "TEST_USER";

    @BeforeEach
    public void setUp() {
        sepUser = new SepUser();
        sepUser.setRole(Role.PRODUCTION_MANAGER);
        sepUser.setActive(true);
        sepUser.setUsername(USERNAME);
        sepUser.setHash(USERNAME);
    }

    @Test
    public void testSave() {
        sepUserRepository.save(sepUser);
        Assertions.assertThat(sepUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindById() {
        Optional<SepUser> sepUser1 = sepUserRepository.findById(ID);
        assertTrue(sepUser1.isPresent());
    }

    @Test
    public void testFindByUsernameIgnoreCase() {
        SepUser sepUser1 = sepUserRepository.findByUsernameIgnoreCase(USERNAME.toLowerCase());
        assertNotNull(sepUser1);
        assertTrue(USERNAME.equalsIgnoreCase(sepUser1.getUsername()));
    }


    @Test
    public void findByRole() {
        List<SepUser> sepUsers = sepUserRepository.findByRole(Role.PRODUCTION_MANAGER);
        assertFalse(sepUsers.isEmpty());
        assertEquals(Role.PRODUCTION_MANAGER, sepUsers.get(0).getRole());
    }

}
