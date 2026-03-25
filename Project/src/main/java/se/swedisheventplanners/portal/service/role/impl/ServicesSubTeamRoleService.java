package se.swedisheventplanners.portal.service.role.impl;

import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.List;

@Service
public class ServicesSubTeamRoleService implements RoleService {

    @Override
    public Role getRole() {
        return Role.SERVICES_SUB_TEAM;
    }

    @Override
    public List<PageLink> getRolePageLinks(Long userId) {
        return List.of(new PageLink("/task/manageMyTasks?assigneeId=" + userId,
                "Manage Tasks",
                "Manage Tasks",
                "Manage Tasks"));
    }
}
