package se.swedisheventplanners.portal.controller.recruitmentrequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.swedisheventplanners.portal.domain.recruitmentrequest.RecruitmentRequest;
import se.swedisheventplanners.portal.model.recruitmentrequest.RecruitmentRequestDto;
import se.swedisheventplanners.portal.service.model.ModelService;
import se.swedisheventplanners.portal.service.recruitmentrequest.RecruitmentRequestService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/recruitmentRequest")
@RequiredArgsConstructor
public class RecruitmentRequestController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;
    private final RecruitmentRequestService recruitmentRequestService;

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @GetMapping("/createRecruitmentRequest")
    public String createRecruitmentRequest(Model model) {
        modelService.addAuthenticationToModel(model);
        return "create_recruitment_request";
    }

    @PreAuthorize("hasAnyAuthority('PRODUCTION_MANAGER', 'SERVICE_MANAGER')")
    @PostMapping("/createRecruitmentRequest")
    public String createRecruitmentRequestPost(HttpServletResponse response, Principal principal, @ModelAttribute RecruitmentRequestDto recruitmentRequestDto) throws IOException {
        RecruitmentRequest recruitmentRequest = modelMapper.map(recruitmentRequestDto, RecruitmentRequest.class);
        recruitmentRequest.setCreatedBy(principal.getName());
        recruitmentRequestService.save(recruitmentRequest);
        response.sendRedirect("/main");
        return "main";
    }

}
