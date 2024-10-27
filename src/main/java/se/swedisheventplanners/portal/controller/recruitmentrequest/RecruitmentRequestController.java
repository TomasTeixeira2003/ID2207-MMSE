package se.swedisheventplanners.portal.controller.recruitmentrequest;

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
import se.swedisheventplanners.portal.domain.planningrequest.Priority;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.recruitmentrequest.RecruitmentRequestDto;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.recruitmentrequest.RecruitmentRequestService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruitmentRequest")
@RequiredArgsConstructor
public class RecruitmentRequestController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final RecruitmentRequestService recruitmentRequestService;

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @GetMapping("/createRecruitmentRequest")
    public String createRecruitmentRequest(Model model) {
        modelService.addAuthenticationToModel(model);
        return "create_recruitment_request";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @PostMapping("/createRecruitmentRequest")
    public String createRecruitmentRequestPost(HttpServletResponse response, Principal principal, @ModelAttribute RecruitmentRequestDto recruitmentRequestDto) throws IOException {
        RecruitmentRequest recruitmentRequest = modelMapper.map(recruitmentRequestDto, RecruitmentRequest.class);
        recruitmentRequest.setCreatedBy(principal.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Role role = Role.valueOf(authentication.getAuthorities().stream()
                .findAny().orElseThrow(() -> new IllegalStateException("No Role for authenticated user")).getAuthority());
        recruitmentRequest.setAssignedToRole(role);
        recruitmentRequestService.save(recruitmentRequest);
        response.sendRedirect("/main");
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER' , 'HR_MANAGER')")
    @GetMapping("/manageRecruitmentRequests")
    public String manageRecruitmentRequests(Model model) {
        modelService.addAuthenticationToModel(model);
        modelService.addPrioritiesToModel(model);
        Role role = (Role) model.getAttribute("userRole");
        List<RecruitmentRequestDto> requests = modelMapper.map(recruitmentRequestService.findByAssignedToRole(role),
                new TypeToken<List<RecruitmentRequest>>(){}.getType());
        model.addAttribute("requests", requests);
        return "manage_recruitment_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @PostMapping("/changePriority")
    public String manageRecruitmentRequests(HttpServletResponse response, @RequestParam Long id, @RequestParam Priority priority) throws IOException {
        recruitmentRequestService.changePriority(id, priority);
        response.sendRedirect("/recruitmentRequest/manageRecruitmentRequests");
        return "manage_recruitment_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @GetMapping("/sendRequestToHR")
    public String sendRequestToHR(HttpServletResponse response, @RequestParam Long id) throws IOException {
        recruitmentRequestService.sendRequestToHR(id);
        response.sendRedirect("/recruitmentRequest/manageRecruitmentRequests");
        return "manage_recruitment_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @GetMapping("/deleteRequest")
    public String deleteRequest(HttpServletResponse response, @RequestParam Long id) throws IOException {
        recruitmentRequestService.deleteRequest(id);
        response.sendRedirect("/recruitmentRequest/manageRecruitmentRequests");
        return "manage_recruitment_requests";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @GetMapping("/editRequest")
    public String editRequest(Model model, @RequestParam Long id) throws IOException {
        modelService.addAuthenticationToModel(model);
        RecruitmentRequestDto recruitmentRequestDto = modelMapper.map(recruitmentRequestService.findById(id), RecruitmentRequestDto.class);
        model.addAttribute("editedRequest", recruitmentRequestDto);
        return "create_recruitment_request";
    }

    @PreAuthorize("hasAnyAuthority('HR_MANAGER')")
    @GetMapping("/viewRequest")
    public String viewRequest(Model model, @RequestParam Long id) {
        modelService.addAuthenticationToModel(model);
        RecruitmentRequestDto recruitmentRequestDto = modelMapper.map(recruitmentRequestService.findById(id), RecruitmentRequestDto.class);
        model.addAttribute("editedRequest", recruitmentRequestDto);
        return "create_recruitment_request";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICES_MANAGER')")
    @PostMapping("/editRequest")
    public String editRequestPost(HttpServletResponse response, @RequestParam Long id, @ModelAttribute RecruitmentRequestDto recruitmentRequestDto) throws IOException {
        RecruitmentRequest recruitmentRequest = modelMapper.map(recruitmentRequestDto, RecruitmentRequest.class);
        recruitmentRequestService.editRequest(id, recruitmentRequest);
        response.sendRedirect("/recruitmentRequest/manageRecruitmentRequests");
        return "manage_recruitment_requests";
    }

    @PreAuthorize("hasAnyAuthority('HR_MANAGER')")
    @GetMapping("/closeRequest")
    public String closeRequest(HttpServletResponse response, @RequestParam Long id) throws IOException {
        recruitmentRequestService.closeRequest(id);
        response.sendRedirect("/recruitmentRequest/manageRecruitmentRequests");
        return "manage_recruitment_requests";
    }

}
