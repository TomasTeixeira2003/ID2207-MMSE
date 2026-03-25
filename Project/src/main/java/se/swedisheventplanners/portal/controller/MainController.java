package se.swedisheventplanners.portal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.swedisheventplanners.portal.service.model.ModelService;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final ModelService modelService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main")
    public String main(Model model) {
        modelService.addAuthenticationToModel(model);
        return "main";
    }

}
