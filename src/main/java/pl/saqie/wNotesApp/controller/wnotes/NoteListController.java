package pl.saqie.wNotesApp.controller.wnotes;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.saqie.wNotesApp.config.UrlProperties;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.service.NoteService;

@Controller
@AllArgsConstructor
public class NoteListController {

    private final NoteService noteService;
    private final UrlProperties urlProperties;

    //Dodaj wszedzie preauthorize

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/list/note")
    public String getHistoryNotePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("noteList", noteService.findAllNotesByUser(user));
        model.addAttribute("adress", urlProperties.getApplicationAdress());
        return "note-list";
    }
}
