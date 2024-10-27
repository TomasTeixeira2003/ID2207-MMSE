package se.swedisheventplanners.portal.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.swedisheventplanners.portal.domain.task.TaskPriority;
import se.swedisheventplanners.portal.domain.task.Task;
import se.swedisheventplanners.portal.domain.task.TaskStatus;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.model.task.TaskDto;
import se.swedisheventplanners.portal.model.user.SepUserDto;
import se.swedisheventplanners.portal.service.SepUserService;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.task.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RoutingController {

    private final ModelService modelService;
    private final SepUserService sepUserService;
    private final TaskService taskService;
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

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @GetMapping("/createTask")
    public String createTask(Model model) {
        modelService.addAuthenticationToModel(model);
        modelService.addAssigneesAndPrioritiesToModel(model);
        return "create_task";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @PostMapping("/editUserRole")
    public String editUserRolePost(@RequestParam Long id, @RequestParam Role role, HttpServletResponse response) throws IOException {
        sepUserService.editUserRole(id, role);
        response.sendRedirect("/manageUsers");
        return "manage_users";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/createTask")
    public String createTaskPost(Model model, @ModelAttribute TaskDto taskDto, HttpServletResponse response) throws IOException {
        Task task = modelMapper.map(taskDto, Task.class);
        task.setStatus(TaskStatus.ASSIGNED);
        taskService.save(task);
        modelService.addAuthenticationToModel(model);
        response.sendRedirect("/main");
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @GetMapping("/manageTasks")
    public String manageTasks(Model model) throws IOException {
        Map<Long, String> userNames = sepUserService.findAll().stream().collect(Collectors.toMap(SepUser::getId, SepUser::getUsername));
        List<TaskDto> tasks = taskService.findAll().stream().map(t -> {
            TaskDto taskDto = modelMapper.map(t, TaskDto.class);
            taskDto.setAssigneeName(userNames.get(t.getAssigneeId()));
            return taskDto;
        }).collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        modelService.addAssigneesAndPrioritiesToModel(model);
        modelService.addAuthenticationToModel(model);
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_SUB_TEAM', 'PRODUCTION_SUB_TEAM')")
    @GetMapping("/manageMyTasks")
    public String manageMyTasks(Model model, @RequestParam Long assigneeId) throws IOException {
        SepUser sepUser = sepUserService.findById(assigneeId);
        List<TaskDto> tasks = taskService.findByAssigneeId(assigneeId).stream().map(t -> {
            TaskDto taskDto = modelMapper.map(t, TaskDto.class);
            taskDto.setAssigneeName(sepUser.getUsername());
            return taskDto;
        }).collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        modelService.addTaskPrioritiesToModel(model);
        modelService.addStatusesToModel(model);
        modelService.addAuthenticationToModel(model);
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @GetMapping("/deleteTask")
    public String deleteTask(Model model, @RequestParam Long id, HttpServletResponse response) throws IOException {
        List<TaskDto> tasks = modelMapper.map(taskService.deleteTask(id), new TypeToken<List<TaskDto>>() {}.getType());
        model.addAttribute("tasks", tasks);
        modelService.addAuthenticationToModel(model);
        modelService.addAssigneesAndPrioritiesToModel(model);
        response.sendRedirect("/manageTasks");
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/changeTaskPriority")
    public String deleteTask(Model model, @RequestParam Long id, @RequestParam TaskPriority priority, HttpServletResponse response) throws IOException {
        List<TaskDto> tasks = modelMapper.map(taskService.changeTaskPriority(id, priority), new TypeToken<List<TaskDto>>() {}.getType());
        model.addAttribute("tasks", tasks);
        modelService.addAuthenticationToModel(model);
        modelService.addAssigneesAndPrioritiesToModel(model);
        response.sendRedirect("/manageTasks");
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/changeTaskAssignee")
    public String changeTaskAssignee(Model model, @RequestParam Long id, @RequestParam Long assigneeId, HttpServletResponse response) throws IOException {
        List<TaskDto> tasks = modelMapper.map(taskService.changeTaskAssignee(id, assigneeId), new TypeToken<List<TaskDto>>() {}.getType());
        model.addAttribute("tasks", tasks);
        modelService.addAuthenticationToModel(model);
        modelService.addAssigneesAndPrioritiesToModel(model);
        response.sendRedirect("/manageTasks");
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_SUB_TEAM', 'PRODUCTION_SUB_TEAM')")
    @PostMapping("/changeTaskStatus")
    public String changeTaskStatus(Model model, @RequestParam Long id, @RequestParam TaskStatus status, HttpServletResponse response) throws IOException {
        List<TaskDto> tasks = modelMapper.map(taskService.changeTaskStatus(id, status), new TypeToken<List<TaskDto>>() {}.getType());
        model.addAttribute("tasks", tasks);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SepUser sepUser = sepUserService.findByUsername(authentication.getName());
        modelService.addAuthenticationToModel(model);
        response.sendRedirect("/manageMyTasks?assigneeId=" + sepUser.getId());
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @GetMapping("/editTask")
    public String editTask(Model model, @RequestParam Long id) {
        TaskDto editedTask = modelMapper.map(taskService.findById(id), TaskDto.class);
        model.addAttribute("editedTask", editedTask);
        modelService.addAuthenticationToModel(model);
        modelService.addAssigneesAndPrioritiesToModel(model);
        return "edit_task";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/editTask")
    public String editTaskPost(@RequestParam Long id, @ModelAttribute TaskDto taskDto, HttpServletResponse response) throws IOException {
        Task editedTask = modelMapper.map(taskDto, Task.class);
        editedTask.setId(id);
        taskService.edit(editedTask);
        response.sendRedirect("/manageTasks");
        return "manage_tasks";
    }

}
