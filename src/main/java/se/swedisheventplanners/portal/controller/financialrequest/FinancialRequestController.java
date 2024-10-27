package se.swedisheventplanners.portal.controller.financialrequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.swedisheventplanners.portal.domain.financialrequest.FinancialRequest;
import se.swedisheventplanners.portal.domain.user.Role;
import se.swedisheventplanners.portal.model.financialrequest.FinancialRequestDto;
import se.swedisheventplanners.portal.service.financialrequest.FinancialRequestService;
import se.swedisheventplanners.portal.service.model.ModelService;

import java.io.IOException;
import java.security.Principal;

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
}
