package pl.saqie.wNotesApp.dto.wnotes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.saqie.wNotesApp.domain.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteTimeDTO {

    @Size(min = 5, message = "Tytuł notatki musi zawierać co najmniej 5 znaków.")
    private String title;
    @Size(min = 2, message = "Treść notatki nie może być pusta.")
    private String content;
    private String uuid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Czas zakończenia notatki nie może być pusty")
    private LocalDate expiredDate;
    private User user;
}
