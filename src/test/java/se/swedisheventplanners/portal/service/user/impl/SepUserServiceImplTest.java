package se.swedisheventplanners.portal.service.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.repository.user.SepUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SepUserServiceImplTest {

    @Mock
    private SepUserRepository repository;

    @InjectMocks
    private SepUserServiceImpl service;

    private SepUser sepUser;
    private static final Long ID = 1L;
    private static final String USERNAME = "bakalis";

    @BeforeEach
    public void setUp() {
        sepUser = new SepUser();
        sepUser.setId(ID);
        sepUser.setRole(Role.PRODUCTION_MANAGER);
        sepUser.setActive(true);
        sepUser.setUsername(USERNAME);
    }

    @Test
    void createSepUser() {
        Mockito.when(repository.save(sepUser)).thenReturn(sepUser);
        SepUser sepUserCreated = service.createSepUser(sepUser);
        assertTrue(sepUserCreated.isActive());
    }

    @Test
    void deactivateUser() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(sepUser));
        SepUser modifiedUser = service.deactivateUser(ID);
        assertFalse(modifiedUser.isActive());
    }

    @Test
    void reactivateUser() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(sepUser));
        SepUser modifiedUser = service.reactivateUser(ID);
        assertTrue(modifiedUser.isActive());
    }

    @Test
    void editUserRole() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(sepUser));
        Role newRole = Role.PRODUCTION_MANAGER;
        SepUser modifiedUser = service.editUserRole(ID, newRole);
        assertEquals(newRole, modifiedUser.getRole());
    }
}