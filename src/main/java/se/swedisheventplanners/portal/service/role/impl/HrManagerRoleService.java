package se.swedisheventplanners.portal.service.role.impl;

import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.List;

@Service
public class HrManagerRoleService implements RoleService {

    @Override
    public Role getRole() {
        return Role.HR_MANAGER;
    }

    @Override
    public List<PageLink> getRolePageLinks(Long userId) {
        return List.of(new PageLink("/recruitmentRequest/manageRecruitmentRequests",
                "Manage Recruitment Requests",
                "Manage Recruitment Requests",
                "Manage Recruitment Requests"));
    }
}
