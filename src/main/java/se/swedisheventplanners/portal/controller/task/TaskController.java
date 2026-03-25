package se.swedisheventplanners.portal.controller.task;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.swedisheventplanners.portal.domain.task.Task;
import se.swedisheventplanners.portal.domain.task.TaskPriority;
import se.swedisheventplanners.portal.domain.task.TaskStatus;
import se.swedisheventplanners.portal.domain.user.SepUser;
import se.swedisheventplanners.portal.model.task.TaskDto;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.user.SepUserService;
import se.swedisheventplanners.portal.service.task.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final ModelService modelService;
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final SepUserService sepUserService;

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @GetMapping("/createTask")
    public String createTask(Model model) {
        modelService.addAuthenticationToModel(model);
        modelService.addAssigneesAndPrioritiesToModel(model);
        return "create_task";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/createTask")
    public String createTaskPost(@ModelAttribute TaskDto taskDto, HttpServletResponse response) throws IOException {
        Task task = modelMapper.map(taskDto, Task.class);
        taskService.save(task);
        response.sendRedirect("/main");
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @GetMapping("/manageTasks")
    public String manageTasks(Model model) {
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
    public String manageMyTasks(Model model, @RequestParam Long assigneeId) {
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
    public String deleteTask(@RequestParam Long id, HttpServletResponse response) throws IOException {
        taskService.deleteTask(id);
        response.sendRedirect("/task/manageTasks");
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/changeTaskPriority")
    public String deleteTask(Model model, @RequestParam Long id, @RequestParam TaskPriority priority, HttpServletResponse response) throws IOException {
        taskService.changeTaskPriority(id, priority);
        response.sendRedirect("/task/manageTasks");
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_MANAGER', 'PRODUCTION_MANAGER')")
    @PostMapping("/changeTaskAssignee")
    public String changeTaskAssignee(Model model, @RequestParam Long id, @RequestParam Long assigneeId, HttpServletResponse response) throws IOException {
        taskService.changeTaskAssignee(id, assigneeId);
        response.sendRedirect("/task/manageTasks");
        return "manage_tasks";
    }

    @PreAuthorize("hasAnyAuthority('SERVICES_SUB_TEAM', 'PRODUCTION_SUB_TEAM')")
    @PostMapping("/changeTaskStatus")
    public String changeTaskStatus(@RequestParam Long id, @RequestParam TaskStatus status, HttpServletResponse response) throws IOException {
        taskService.changeTaskStatus(id, status);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SepUser sepUser = sepUserService.findByUsername(authentication.getName());
        response.sendRedirect("/task/manageMyTasks?assigneeId=" + sepUser.getId());
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
        response.sendRedirect("/task/manageTasks");
        return "manage_tasks";
    }
}
