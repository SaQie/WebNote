package pl.saqie.wNotesApp.controller.wnotes;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.saqie.wNotesApp.config.UrlProperties;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.wnotes.NoteDTO;
import pl.saqie.wNotesApp.service.NoteService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class NewNoteController {

    private final NoteService noteService;
    private final UrlProperties urlProperties;

    @GetMapping("/wnotes/new/note")
    public String getNewNotePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute(new NoteDTO());
        model.addAttribute("saveNoteSuccess", false);
        return "note-anonim";
    }

    @PostMapping("/wnotes/new/note")
    public String getNewNoteFromInput(@ModelAttribute @Valid NoteDTO note, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("saveNoteSuccess", false);
        if (!bindingResult.hasErrors()) {
            model.addAttribute(noteService.saveNewNote(note, user));
            model.addAttribute("saveNoteSuccess", true);
            model.addAttribute("adress", urlProperties.getApplicationAdress());
        }
        return "note-anonim";
    }
}
