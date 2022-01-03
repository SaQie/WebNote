package pl.saqie.wNotesApp.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.saqie.wNotesApp.dto.auth.ForgotPasswordDTO;
import pl.saqie.wNotesApp.dto.auth.ResetPasswordDTO;
import pl.saqie.wNotesApp.exceptions.PasswordDoNotMatch;
import pl.saqie.wNotesApp.exceptions.TokenNotFoundException;
import pl.saqie.wNotesApp.exceptions.UserNotFoundException;
import pl.saqie.wNotesApp.service.EmailService;
import pl.saqie.wNotesApp.service.PasswordResetService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@AllArgsConstructor
public class ForgotPasswordController {

    private final PasswordResetService passwordService;
    private final EmailService emailService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/forgot_password")
    public String getFrogotPasswordPage(Model model) {
        model.addAttribute("forgotPassword", new ForgotPasswordDTO());
        return "forgot-password";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/forgot_password")
    public String forgotPassword(@ModelAttribute("forgotPassword") @Valid ForgotPasswordDTO forgotPasswordDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "forgot-password";
        } else {
            try {
                String token = passwordService.updateResetPasswordTokenToUser(forgotPasswordDTO.getEmail());
                emailService.sendEmail(forgotPasswordDTO.getEmail(), token, "FORGOTPASSWORD");
                model.addAttribute("resetSucces", true);
            } catch (UserNotFoundException exception) {
                model.addAttribute("error", exception.getMessage());
            } catch (MessagingException | UnsupportedEncodingException exception) {
                model.addAttribute("error", "Błąd, nie udało się wysłać E-Maila, skontaktuj się z administratorem strony.");
            }
            return "forgot-password";
        }
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam(value = "token", defaultValue = "") String token, Model model) {
        try {
            ResetPasswordDTO user = passwordService.getUserByResetPasswordToken(token);
            model.addAttribute("user", user);
        } catch (TokenNotFoundException exception) {
            model.addAttribute("foundError", exception.getMessage());
        }
        return "reset-password";
    }

    @PostMapping("/reset_password")
    public String resetUserPassword(@ModelAttribute("user") @Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "reset-password";
        } else {
            try {
                passwordService.updatePassword(resetPasswordDTO);
                model.addAttribute("resetPassword", true);
                return "login";
            } catch (UserNotFoundException | PasswordDoNotMatch exception) {
                model.addAttribute("error", exception.getMessage());
            }
            return "reset-password";
        }
    }
}
