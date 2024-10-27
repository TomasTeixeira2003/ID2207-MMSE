package se.swedisheventplanners.portal.controller.planningrequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.swedisheventplanners.portal.domain.planningrequest.EventPlanningRequest;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.planningrequest.EventPlanningRequestDto;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.planningrequest.EventPlanningRequestService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/planningRequest")
@RequiredArgsConstructor
public class PlanningRequestController {

    private final ModelMapper modelMapper;
    private final ModelService modelService;
    private final EventPlanningRequestService eventPlanningRequestService;

    @PreAuthorize("hasAuthority('CUSTOMER_SUPPORT_OFFICER')")
    @GetMapping("/createNewPlanningRequest")
    public String createNewPlanningRequest(Model model) {
        modelService.addAuthenticationToModel(model);
        return "create_planning_request";
    }

    @PreAuthorize("hasAuthority('CUSTOMER_SUPPORT_OFFICER')")
    @PostMapping("/createNewPlanningRequest")
    public String createNewPlanningRequestPost(Model model, @ModelAttribute EventPlanningRequestDto eventPlanningRequestDto, HttpServletResponse httpServletResponse, Principal principal) throws IOException {
        EventPlanningRequest eventPlanningRequest = modelMapper.map(eventPlanningRequestDto, EventPlanningRequest.class);
        eventPlanningRequest.setRequestedBy(principal.getName());
        eventPlanningRequestService.save(eventPlanningRequest);
        httpServletResponse.sendRedirect("/main");
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER_SUPPORT_OFFICER', 'SENIOR_CUSTOMER_SUPPORT_OFFICER', 'FINANCIAL_MANAGER', 'ADMINISTRATION_MANAGER')")
    @GetMapping("/managePlanningRequests")
    public String managePlanningRequests(Model model) {
        modelService.addAuthenticationToModel(model);
        Role role = (Role) model.getAttribute("userRole");
        List<EventPlanningRequestDto> requests = modelMapper.map(eventPlanningRequestService.findByAssignedToRole(role),
                new TypeToken<List<EventPlanningRequestDto>>(){}.getType());
        model.addAttribute("requests", requests);
        return "manage_planning_requests";
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER_SUPPORT_OFFICER', 'SENIOR_CUSTOMER_SUPPORT_OFFICER', 'FINANCIAL_MANAGER', 'ADMINISTRATION_MANAGER')")
    @GetMapping("/sendRequest")
    public String sendRequest(@RequestParam Long id, @RequestParam Role role, HttpServletResponse httpServletResponse) throws IOException {
        eventPlanningRequestService.sendRequest(id, role);
        httpServletResponse.sendRedirect("/planningRequest/managePlanningRequests");
        return "manage_planning_requests";
    }

    @PreAuthorize("hasAnyAuthority('SENIOR_CUSTOMER_SUPPORT_OFFICER', 'FINANCIAL_MANAGER', 'ADMINISTRATION_MANAGER')")
    @GetMapping("/rejectRequest")
    public String rejectRequest(@RequestParam Long id, HttpServletResponse httpServletResponse) throws IOException {
        eventPlanningRequestService.rejectRequest(id);
        httpServletResponse.sendRedirect("/planningRequest/managePlanningRequests");
        return "manage_planning_requests";
    }

    @PreAuthorize("hasAuthority('ADMINISTRATION_MANAGER')")
    @GetMapping("/approveRequest")
    public String approveRequest(@RequestParam Long id, HttpServletResponse httpServletResponse) throws IOException {
        eventPlanningRequestService.approveRequest(id);
        httpServletResponse.sendRedirect("/planningRequest/managePlanningRequests");
        return "manage_planning_requests";
    }

    @PreAuthorize("hasAuthority('SENIOR_CUSTOMER_SUPPORT_OFFICER')")
    @GetMapping("/closeRequest")
    public String closeRequest(@RequestParam Long id, HttpServletResponse httpServletResponse) throws IOException {
        eventPlanningRequestService.closeRequest(id);
        httpServletResponse.sendRedirect("/planningRequest/managePlanningRequests");
        return "manage_planning_requests";
    }

    @PreAuthorize("hasAuthority('SENIOR_CUSTOMER_SUPPORT_OFFICER')")
    @GetMapping("/archiveRequest")
    public String archiveRequest(@RequestParam Long id, HttpServletResponse httpServletResponse) throws IOException {
        eventPlanningRequestService.archiveRequest(id);
        httpServletResponse.sendRedirect("/planningRequest/managePlanningRequests");
        return "manage_planning_requests";
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER_SUPPORT_OFFICER', 'SENIOR_CUSTOMER_SUPPORT_OFFICER', 'FINANCIAL_MANAGER', 'ADMINISTRATION_MANAGER')")
    @GetMapping("/editRequest")
    public String editRequest(@RequestParam Long id, Model model) throws IOException {
        modelService.addAuthenticationToModel(model);
        EventPlanningRequestDto eventPlanningRequestDto = modelMapper.map(eventPlanningRequestService.findById(id), EventPlanningRequestDto.class);
        model.addAttribute("editedRequest", eventPlanningRequestDto);
        return "create_planning_request";
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER_SUPPORT_OFFICER', 'SENIOR_CUSTOMER_SUPPORT_OFFICER', 'FINANCIAL_MANAGER', 'ADMINISTRATION_MANAGER')")
    @PostMapping("/editPlanningRequest")
    public String editRequest(@RequestParam Long id, @ModelAttribute EventPlanningRequestDto eventPlanningRequestDto, HttpServletResponse response) throws IOException {
        EventPlanningRequest eventPlanningRequest = modelMapper.map(eventPlanningRequestDto, EventPlanningRequest.class);
        eventPlanningRequestService.editRequest(id, eventPlanningRequest);
        response.sendRedirect("/planningRequest/managePlanningRequests");
        return "manage_planning_requests";
    }
}
