package se.swedisheventplanners.portal.controller.planningrequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PreAuthorize("hasAuthority('CUSTOMER_SUPPORT_OFFICER')")
    @GetMapping("/managePlanningRequests")
    public String managePlanningRequests(Model model) {
        modelService.addAuthenticationToModel(model);
        Role role = (Role) model.getAttribute("userRole");
        List<EventPlanningRequestDto> requests = modelMapper.map(eventPlanningRequestService.findByAssignedToRole(role),
                new TypeToken<List<EventPlanningRequestDto>>(){}.getType());
        model.addAttribute("requests", requests);
        return "manage_planning_requests";
    }
}
