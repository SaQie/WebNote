package pl.saqie.wNotesApp.controller.wnotes;

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
import pl.saqie.wNotesApp.dto.wnotes.NoteTimeDTO;
import pl.saqie.wNotesApp.service.NoteService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class NoteTimeController {

    private final NoteService noteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/time/note")
    public String getTimeNotePage(Model model) {
        model.addAttribute(new NoteTimeDTO());
        model.addAttribute("saveNoteSuccess", false);
        return "note-time";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/wnotes/time/note")
    public String createNewTimeNote(@ModelAttribute @Valid NoteTimeDTO noteTimeDTO, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "note-time";
        }
        noteService.saveNewTimeNote(noteTimeDTO, user);
        model.addAttribute("saveSuccesfull", true);
        return "menu";
    }

}
