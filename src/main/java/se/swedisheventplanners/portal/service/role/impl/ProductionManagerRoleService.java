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
    public List<PageLink> getRolePageLinks(Long userId) {
        return List.of(new PageLink("/task/createTask",
                "Create a New Task",
                "Create a New Task",
                "Create Task"),
                new PageLink("/task/manageTasks",
                "Manage Tasks",
                        "Manage Tasks",
                "Manage Tasks"),
                new PageLink("/recruitmentRequest/createRecruitmentRequest",
                "Create Recruitment Request",
                 "Create Recruitment Request",
                "Create Recruitment Request"),
                new PageLink("/recruitmentRequest/manageRecruitmentRequests",
                "Manage Recruitment Requests",
                 "Manage Recruitment Requests",
                "Manage Recruitment Requests"),
                new PageLink("/financialRequest/createFinancialRequest",
                "Create Financial Request",
                "Create Financial Request",
                "Create Financial Request"),
                new PageLink("/financialRequest/manageFinancialRequests",
                "Manage Financial Requests",
                "Manage Financial Requests",
                "Manage Financial Requests"));
    }
}
