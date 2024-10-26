package se.swedisheventplanners.portal.service.role.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionManagerRoleService implements RoleService {

    @Override
    public Role getRole() {
        return Role.PRODUCTION_MANAGER;
    }

    @Override
    public List<PageLink> getRolePageLinks() {
        return List.of(new PageLink("/createTask",
                "Create a New Task",
                "Create a New Task",
                "Create Task"));
    }
}
