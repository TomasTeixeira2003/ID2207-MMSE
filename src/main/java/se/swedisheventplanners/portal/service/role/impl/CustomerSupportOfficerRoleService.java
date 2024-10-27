package se.swedisheventplanners.portal.service.role.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.service.role.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerSupportOfficerRoleService implements RoleService {

    @Override
    public Role getRole() {
        return Role.CUSTOMER_SUPPORT_OFFICER;
    }

    @Override
    public List<PageLink> getRolePageLinks(Long userId) {
        return List.of(new PageLink("/planningRequest/createNewPlanningRequest",
                "Create a New Event Planning Request",
                "Start a new Event Planning Request from scratch",
                "Create Event Planning Request"),
                new PageLink("/planningRequest/managePlanningRequests",
                "View Planning Requests",
                "View Planning Requests",
                "View Planning Requests"));
    }
}
