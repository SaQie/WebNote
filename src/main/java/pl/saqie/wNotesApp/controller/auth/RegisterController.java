package pl.saqie.wNotesApp.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.saqie.wNotesApp.dto.auth.RegisterDTO;
import pl.saqie.wNotesApp.exceptions.*;
import pl.saqie.wNotesApp.service.RegisterService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute(new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegisterUser(@ModelAttribute @Valid RegisterDTO registerDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            try {
                registerService.registerNewUser(registerDTO);
                model.addAttribute("registerSuccess", true);
            } catch (PasswordDoNotMatch | EmailAlreadyExistsException | UsernameAlreadyExistsException exception) {
                model.addAttribute("error", exception.getMessage());
            } catch (MessagingException | UnsupportedEncodingException exception) {
                model.addAttribute("error", "Błąd, nie udało się wysłać E-Maila, skontaktuj się z administratorem strony.");
            }
        }
        return "register";
    }

    @GetMapping("/activation")
    public String activateUserAccount(@RequestParam(value = "token", defaultValue = "") String token, Model model) {
        try {
            registerService.enableUserAccount(token);
            model.addAttribute("enableAccountSuccess", true);
        } catch (ActivationTimeExpiredException | UserNotFoundException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "activate-account";
    }
}
