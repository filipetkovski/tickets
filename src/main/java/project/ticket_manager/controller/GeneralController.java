package project.ticket_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.ticket_manager.security.SecurityUtil;

@Controller
public class GeneralController {

    @GetMapping("/")
    public String redirectHomePage() {
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            return "redirect:/ticket/create";
        }
        return "redirect:/login";
    }
}
