package pl.saqie.wNotesApp.controller.wnotes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/home";
    }

    @PostMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }


}
