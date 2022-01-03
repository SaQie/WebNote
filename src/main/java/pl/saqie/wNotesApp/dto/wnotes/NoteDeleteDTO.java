package pl.saqie.wNotesApp.dto.wnotes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDeleteDTO {

    private Long id;
    private String title;
    private String content;

}