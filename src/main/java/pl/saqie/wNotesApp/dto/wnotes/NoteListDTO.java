package pl.saqie.wNotesApp.dto.wnotes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteListDTO {

    private String uuid;
    private String title;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private LocalDate expiredDate;
    private boolean privateNote;
}
