package pl.saqie.wNotesApp.controller.wnotes;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.wnotes.NoteDeleteDTO;
import pl.saqie.wNotesApp.exceptions.BadActionException;
import pl.saqie.wNotesApp.exceptions.NoteBadOwnerException;
import pl.saqie.wNotesApp.exceptions.NoteNotFoundException;
import pl.saqie.wNotesApp.service.NoteService;

@Controller
@AllArgsConstructor
public class NoteDeleteController {

    private final NoteService noteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/delete/{uuid}")
    public String getNoteToDelete(@PathVariable String uuid, @AuthenticationPrincipal User user, Model model) {
        try {
            NoteDeleteDTO noteToDelete = (NoteDeleteDTO) noteService.checkNoteAndMapToRightDto(uuid, user, "DELETE");
            model.addAttribute("note", noteToDelete);
        } catch (NoteNotFoundException | BadActionException | NoteBadOwnerException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "note-delete-page";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/wnotes/delete")
    public String deleteNote(@ModelAttribute NoteDeleteDTO note, Model model) {
        noteService.deleteNote(note.getId());
        model.addAttribute("deleteSuccess", true);
        return "menu";
    }
}


