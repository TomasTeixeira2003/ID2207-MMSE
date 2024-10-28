package se.swedisheventplanners.portal.controller.user;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final SepUserService sepUserService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/createNewUser")
    public String createNewUser(Model model) {
        modelService.addAuthenticationToModel(model);
        model.addAttribute("roles", Role.values());
        return "create_user";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @PostMapping("/createNewUser")
    public String createNewUserPost(@ModelAttribute SepUserDto sepUserDto, HttpServletResponse response) throws IOException {
        SepUser sepUser = modelMapper.map(sepUserDto, SepUser.class);
        sepUser.setHash(passwordEncoder.encode(sepUserDto.getHash()));
        sepUserService.createSepUser(sepUser);
        response.sendRedirect("/main");
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
    public String deactivateUser(@RequestParam Long id, HttpServletResponse response) throws IOException {
        sepUserService.deactivateUser(id);
        response.sendRedirect("/user/manageUsers");
        return "manage_users";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/reactivateUser")
    public String reactivateUser(@RequestParam Long id,  HttpServletResponse response) throws IOException {
        sepUserService.reactivateUser(id);
        response.sendRedirect("/user/manageUsers");
        return "manage_users";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id, HttpServletResponse response) throws IOException {
        sepUserService.deleteUser(id);
        response.sendRedirect("/user/manageUsers");
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
        response.sendRedirect("/user/manageUsers");
        return "manage_users";
    }
}
