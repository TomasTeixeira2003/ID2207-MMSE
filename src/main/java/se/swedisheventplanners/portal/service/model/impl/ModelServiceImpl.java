package se.swedisheventplanners.portal.service.model.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.task.TaskPriority;
import se.swedisheventplanners.portal.domain.task.TaskStatus;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.model.user.SepUserDto;
import se.swedisheventplanners.portal.service.user.SepUserService;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.role.RoleServiceFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final SepUserService sepUserService;
    private final RoleServiceFactory roleServiceFactory;
    private final ModelMapper modelMapper;

    public static final String USER = "user";
    public static final String USER_ROLE = "userRole";
    public static final String LINKS = "links";
    public static final String SEP_USERS = "sepUsers";
    public static final String PRIORITIES = "priorities";
    public static final String STATUSES = "statuses";

    @Override
    public void addAuthenticationToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SepUser sepUser = sepUserService.findByUsername(authentication.getName());
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        List<PageLink> linksForUser = roleServiceFactory.getRoleService(role).getRolePageLinks(sepUser.getId());
        model.addAttribute(USER, authentication.getName());
        model.addAttribute(USER_ROLE, role);
        model.addAttribute(LINKS, linksForUser);
    }

    @Override
    public void addAssigneesAndPrioritiesToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        Role subTeamRole = switch (role) {
            case SERVICES_MANAGER -> Role.SERVICES_SUB_TEAM;
            case PRODUCTION_MANAGER -> Role.PRODUCTION_SUB_TEAM;
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
        List<SepUserDto> sepUsers = modelMapper.map(sepUserService.findByRole(subTeamRole), new TypeToken<List<SepUserDto>>() {}.getType());
        model.addAttribute(SEP_USERS, sepUsers);
        addTaskPrioritiesToModel(model);
        addStatusesToModel(model);
    }

    @Override
    public void addTaskPrioritiesToModel(Model model) {
        model.addAttribute(PRIORITIES, TaskPriority.values());
    }

    @Override
    public void addPrioritiesToModel(Model model) {
        model.addAttribute(PRIORITIES, Priority.values());
    }

    @Override
    public void addStatusesToModel(Model model) {
        model.addAttribute(STATUSES, TaskStatus.values());
    }
}
