package se.swedisheventplanners.portal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.model.RoleDto;
import se.swedisheventplanners.portal.model.routing.PageLink;
import se.swedisheventplanners.portal.model.user.SepUserDto;
import se.swedisheventplanners.portal.service.SepUserService;
import se.swedisheventplanners.portal.service.role.RoleServiceFactory;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RoutingController {

    private final RoleServiceFactory roleServiceFactory;
    private final SepUserService sepUserService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model) {
        addAuthenticationToModel(model);
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/createNewUser")
    public String createNewUser(Model model) {
        addAuthenticationToModel(model);
        model.addAttribute("roles", Arrays.stream(Role.values()).map(x -> new RoleDto(x.getLabel(), x.name())));
        return "create_user";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @PostMapping("/createNewUser")
    public String createNewUserPost(Model model, @ModelAttribute SepUserDto sepUserDto) {
        SepUser sepUser = modelMapper.map(sepUserDto, SepUser.class);
        sepUser.setHash(passwordEncoder.encode(sepUserDto.getHash()));
        sepUserService.createSepUser(sepUser);
        addAuthenticationToModel(model);
        return "main";
    }

    private void addAuthenticationToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        List<PageLink> linksForUser = roleServiceFactory.getRoleService(role).getRolePageLinks();
        model.addAttribute("user", authentication.getName());
        model.addAttribute("links", linksForUser);
    }

}
