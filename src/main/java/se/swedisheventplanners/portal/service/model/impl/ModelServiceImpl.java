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
import se.swedisheventplanners.portal.service.SepUserService;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.role.RoleServiceFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final SepUserService sepUserService;
    private final RoleServiceFactory roleServiceFactory;
    private final ModelMapper modelMapper;

    @Override
    public void addAuthenticationToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SepUser sepUser = sepUserService.findByUsername(authentication.getName());
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        List<PageLink> linksForUser = roleServiceFactory.getRoleService(role).getRolePageLinks(sepUser.getId());
        model.addAttribute("user", authentication.getName());
        model.addAttribute("userRole", role);
        model.addAttribute("links", linksForUser);

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
        model.addAttribute("sepUsers", sepUsers);
        addTaskPrioritiesToModel(model);
        addStatusesToModel(model);
    }

    @Override
    public void addTaskPrioritiesToModel(Model model) {
        model.addAttribute("priorities", TaskPriority.values());
    }

    @Override
    public void addPrioritiesToModel(Model model) {
        model.addAttribute("priorities", Priority.values());
    }

    @Override
    public void addStatusesToModel(Model model) {
        model.addAttribute("statuses", TaskStatus.values());
    }
}
