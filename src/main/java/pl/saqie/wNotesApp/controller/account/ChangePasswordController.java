package pl.saqie.wNotesApp.controller.account;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.auth.ChangePasswordDTO;
import pl.saqie.wNotesApp.exceptions.PasswordDoNotMatch;
import pl.saqie.wNotesApp.exceptions.UserNotFoundException;
import pl.saqie.wNotesApp.mapper.RegisterDtoMapper;
import pl.saqie.wNotesApp.service.PasswordResetService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ChangePasswordController {

    private final PasswordResetService resetService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/account/change_password")
    public String getResetPasswordPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(RegisterDtoMapper.mapToChangePasswordDTO(user));
        return "change-password";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/wnotes/account/change_password")
    public String processChangePassword(@ModelAttribute(name = "changePasswordDTO") @Valid ChangePasswordDTO changePasswordDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "change-password";
        } else {
            try {
                resetService.changePassword(changePasswordDTO);
                return "redirect:/logout";
            } catch (PasswordDoNotMatch | UserNotFoundException exception) {
                model.addAttribute("error", exception.getMessage());
            }
            return "change-password";
        }
    }
}
