package pl.saqie.wNotesApp.controller.wnotes;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.wnotes.NoteDTO;
import pl.saqie.wNotesApp.exceptions.NoteBadOwnerException;
import pl.saqie.wNotesApp.exceptions.NoteExpiredException;
import pl.saqie.wNotesApp.exceptions.NoteNotFoundException;
import pl.saqie.wNotesApp.service.NoteService;

@Controller
@AllArgsConstructor
public class NoteReadController {

    private final NoteService noteService;


    @GetMapping("/note/{uuid}")
    public String getNotePage(@PathVariable String uuid, @AuthenticationPrincipal User user, Model model) {
        try {
            NoteDTO noteByUUID = noteService.findNote(uuid, user);
            model.addAttribute("noteByUUID", noteByUUID);
        } catch (NoteNotFoundException | NoteExpiredException | NoteBadOwnerException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "view-note";
    }

}
