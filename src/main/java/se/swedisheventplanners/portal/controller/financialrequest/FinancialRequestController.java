package se.swedisheventplanners.portal.controller.financialrequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.financialrequest.FinancialRequestDto;
import se.swedisheventplanners.portal.service.financialrequest.FinancialRequestService;
import se.swedisheventplanners.portal.service.model.ModelService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/financialRequest")
@RequiredArgsConstructor
public class FinancialRequestController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final FinancialRequestService financialRequestService;

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @GetMapping("/createFinancialRequest")
    public String createFinancialRequest(Model model) {
        modelService.addAuthenticationToModel(model);
        return "create_financial_request";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @PostMapping("/createFinancialRequest")
    public String createFinancialRequestPost(HttpServletResponse response, Principal principal, @ModelAttribute FinancialRequestDto financialRequestDto) throws IOException {
        FinancialRequest financialRequest = modelMapper.map(financialRequestDto, FinancialRequest.class);
        financialRequest.setCreatedBy(principal.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        financialRequest.setAssignedToRole(role);
        financialRequestService.save(financialRequest);
        response.sendRedirect("/main");
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER' , 'FINANCIAL_MANAGER')")
    @GetMapping("/manageFinancialRequests")
    public String manageFinancialRequests(Model model) {
        modelService.addAuthenticationToModel(model);
        modelService.addPrioritiesToModel(model);
        Role role = (Role) model.getAttribute("userRole");
        List<FinancialRequestDto> requests = modelMapper.map(financialRequestService.findByAssignedToRole(role),
                new TypeToken<List<FinancialRequest>>(){}.getType());
        model.addAttribute("requests", requests);
        return "manage_financial_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @PostMapping("/changePriority")
    public String manageFinancialRequests(HttpServletResponse response, @RequestParam Long id, @RequestParam Priority priority) throws IOException {
        financialRequestService.changePriority(id, priority);
        response.sendRedirect("/financialRequest/manageFinancialRequests");
        return "manage_financial_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @GetMapping("/sendRequestToFinancialManager")
    public String sendRequestToFinancialManager(HttpServletResponse response, @RequestParam Long id) throws IOException {
        financialRequestService.sendRequestToFinancialManager(id);
        response.sendRedirect("/financialRequest/manageFinancialRequests");
        return "manage_financial_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @GetMapping("/deleteRequest")
    public String deleteRequest(HttpServletResponse response, @RequestParam Long id) throws IOException {
        financialRequestService.deleteRequest(id);
        response.sendRedirect("/financialRequest/manageFinancialRequests");
        return "manage_financial_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @GetMapping("/editRequest")
    public String editRequest(Model model, @RequestParam Long id) throws IOException {
        modelService.addAuthenticationToModel(model);
        FinancialRequestDto financialRequestDto = modelMapper.map(financialRequestService.findById(id), FinancialRequestDto.class);
        model.addAttribute("editedRequest", financialRequestDto);
        return "create_financial_request";
    }

    @PreAuthorize("hasAnyAuthority('FINANCIAL_MANAGER')")
    @GetMapping("/viewRequest")
    public String viewRequest(Model model, @RequestParam Long id) {
        modelService.addAuthenticationToModel(model);
        FinancialRequestDto financialRequestDto = modelMapper.map(financialRequestService.findById(id), FinancialRequestDto.class);
        model.addAttribute("editedRequest", financialRequestDto);
        return "create_financial_request";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @PostMapping("/editRequest")
    public String editRequestPost(HttpServletResponse response, @RequestParam Long id, @ModelAttribute FinancialRequestDto financialRequestDto) throws IOException {
        FinancialRequest financialRequest = modelMapper.map(financialRequestDto, FinancialRequest.class);
        financialRequestService.editRequest(id, financialRequest);
        response.sendRedirect("/financialRequest/manageFinancialRequests");
        return "manage_financial_requests";
    }

    @PreAuthorize("hasAnyAuthority('FINANCIAL_MANAGER')")
    @GetMapping("/approveRequest")
    public String approveRequest(HttpServletResponse response, @RequestParam Long id) throws IOException {
        financialRequestService.approveRequest(id);
        response.sendRedirect("/financialRequest/manageFinancialRequests");
        return "manage_financial_requests";
    }

    @PreAuthorize("hasAnyAuthority('FINANCIAL_MANAGER')")
    @GetMapping("/rejectRequest")
    public String rejectRequest(HttpServletResponse response, @RequestParam Long id) throws IOException {
        financialRequestService.rejectRequest(id);
        response.sendRedirect("/financialRequest/manageFinancialRequests");
        return "manage_financial_requests";
    }
}
