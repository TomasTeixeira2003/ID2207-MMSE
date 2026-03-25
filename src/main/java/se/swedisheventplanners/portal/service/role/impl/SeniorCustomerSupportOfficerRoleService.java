package se.swedisheventplanners.portal.service.role.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeniorCustomerSupportOfficerRoleService implements RoleService {

    @Override
    public Role getRole() {
        return Role.SENIOR_CUSTOMER_SUPPORT_OFFICER;
    }

    @Override
    public List<PageLink> getRolePageLinks(Long userId) {
        return List.of(new PageLink("/planningRequest/managePlanningRequests",
                "View Planning Requests",
                "View Planning Requests",
                "View Planning Requests"));
    }
}
