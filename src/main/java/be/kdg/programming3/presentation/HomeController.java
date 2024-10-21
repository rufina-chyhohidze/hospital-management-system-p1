package be.kdg.programming3.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }
//
}
