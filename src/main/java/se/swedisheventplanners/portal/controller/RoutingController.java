package se.swedisheventplanners.portal.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.model.user.SepUserDto;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.user.SepUserService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RoutingController {

    private final ModelService modelService;
    private final SepUserService sepUserService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model) {
        modelService.addAuthenticationToModel(model);
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/createNewUser")
    public String createNewUser(Model model) {
        modelService.addAuthenticationToModel(model);
        model.addAttribute("roles", Role.values());
        return "create_user";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @PostMapping("/createNewUser")
    public String createNewUserPost(Model model, @ModelAttribute SepUserDto sepUserDto) {
        SepUser sepUser = modelMapper.map(sepUserDto, SepUser.class);
        sepUser.setHash(passwordEncoder.encode(sepUserDto.getHash()));
        sepUserService.createSepUser(sepUser);
        modelService.addAuthenticationToModel(model);
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/manageUsers")
    public String viewUsers(Model model) {
        List<SepUserDto> sepUsers = modelMapper.map(sepUserService.findAll(), new TypeToken<List<SepUserDto>>() {}.getType());
        modelService.addAuthenticationToModel(model);
        model.addAttribute("sepUsers", sepUsers);
        return "manage_users";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/deactivateUser")
    public String deactivateUser(Model model, @RequestParam Long id, HttpServletResponse response) throws IOException {
        List<SepUserDto> sepUsers = modelMapper.map(sepUserService.deactivateUser(id), new TypeToken<List<SepUserDto>>() {}.getType());
        modelService.addAuthenticationToModel(model);
        model.addAttribute("sepUsers", sepUsers);
        response.sendRedirect("/manageUsers");
        return "manage_users";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/reactivateUser")
    public String reactivateUser(Model model, @RequestParam Long id,  HttpServletResponse response) throws IOException {
        List<SepUserDto> sepUsers = modelMapper.map(sepUserService.reactivateUser(id), new TypeToken<List<SepUserDto>>() {}.getType());
        modelService.addAuthenticationToModel(model);
        model.addAttribute("sepUsers", sepUsers);
        response.sendRedirect("/manageUsers");
        return "manage_users";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/deleteUser")
    public String deleteUser(Model model, @RequestParam Long id, HttpServletResponse response) throws IOException {
        List<SepUserDto> sepUsers = modelMapper.map(sepUserService.deleteUser(id), new TypeToken<List<SepUserDto>>() {}.getType());
        modelService.addAuthenticationToModel(model);
        model.addAttribute("sepUsers", sepUsers);
        response.sendRedirect("/manageUsers");
        return "manage_users";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/editUserRole")
    public String editUserRole(Model model, @RequestParam Long id) {
        SepUserDto user = modelMapper.map(sepUserService.findById(id), SepUserDto.class);
        modelService.addAuthenticationToModel(model);
        model.addAttribute("editedUser", user);
        model.addAttribute("roles", Role.values());
        return "edit_user_role";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @PostMapping("/editUserRole")
    public String editUserRolePost(@RequestParam Long id, @RequestParam Role role, HttpServletResponse response) throws IOException {
        sepUserService.editUserRole(id, role);
        response.sendRedirect("/manageUsers");
        return "manage_users";
    }

}
