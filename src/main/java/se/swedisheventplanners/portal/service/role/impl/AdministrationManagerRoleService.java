package se.swedisheventplanners.portal.service.role.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrationManagerRoleService implements RoleService {

    @Override
    public Role getRole() {
        return Role.ADMINISTRATION_MANAGER;
    }

    @Override
    public List<PageLink> getRolePageLinks() {
        return List.of(new PageLink("/createNewUser",
                "Create a New SEP User",
                "Create a New SEP User",
                "Create User"));
    }
}
