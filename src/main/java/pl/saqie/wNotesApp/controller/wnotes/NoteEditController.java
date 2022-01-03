package pl.saqie.wNotesApp.controller.wnotes;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.wnotes.NoteEditDTO;
import pl.saqie.wNotesApp.exceptions.BadActionException;
import pl.saqie.wNotesApp.exceptions.NoteBadOwnerException;
import pl.saqie.wNotesApp.exceptions.NoteNotFoundException;
import pl.saqie.wNotesApp.service.NoteService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class NoteEditController {

    private final NoteService noteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/edit/{uuid}")
    public String getNoteEditPage(@PathVariable String uuid, @AuthenticationPrincipal User user, Model model) {
        try {
            NoteEditDTO noteToEdit = (NoteEditDTO) noteService.checkNoteAndMapToRightDto(uuid, user, "EDIT");
            model.addAttribute("note", noteToEdit);
        } catch (NoteNotFoundException | BadActionException | NoteBadOwnerException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "note-edit-page";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/wnotes/edit")
    public String updateNote(@ModelAttribute("note") @Valid NoteEditDTO noteDTO, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                noteService.updateNote(noteDTO, user);
                model.addAttribute("editSuccesfull", true);
                return "menu";
            } catch (NoteNotFoundException | NoteBadOwnerException exception) {
                model.addAttribute("error", exception.getMessage());
            }
        }
        return "note-edit-page";
    }
}
