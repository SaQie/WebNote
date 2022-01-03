package pl.saqie.wNotesApp.dto.wnotes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.saqie.wNotesApp.domain.User;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {

    @Size(min = 5, message = "Tytuł notatki musi zawierać co najmniej 5 znaków.")
    private String title;
    @Size(min = 2, message = "Treść notatki nie może być pusta.")
    private String content;
    private String uuid;
    private User user;
}
