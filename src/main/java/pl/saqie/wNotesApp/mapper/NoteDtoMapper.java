package pl.saqie.wNotesApp.mapper;

import org.springframework.stereotype.Service;
import pl.saqie.wNotesApp.domain.Note;
import pl.saqie.wNotesApp.dto.wnotes.*;

@Service
public class NoteDtoMapper {

    public static Note mapNoteDtoToNote(NoteDTO noteDTO) {
        return Note.builder()
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .uuid(noteDTO.getUuid())
                .build();
    }

    public static NoteListDTO mapNoteToNoteListDto(Note note) {
        return NoteListDTO.builder()
                .uuid(note.getUuid())
                .title(note.getTitle())
                .privateNote(note.isPrivNote())
                .updatedDate(note.getUpdatedDate())
                .createdDate(note.getCreatedDate())
                .expiredDate(note.getExpiredDate())
                .build();
    }


    public static NoteDTO mapNoteToNoteDto(Note note) {
        return NoteDTO.builder()
                .uuid(note.getUuid())
                .content(note.getContent())
                .title(note.getTitle())
                .build();
    }

    public static NoteEditDTO mapNoteToNoteEditDto(Note note) {
        return NoteEditDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdDate(note.getCreatedDate())
                .uuid(note.getUuid())
                .user(note.getUser())
                .expiredDate(note.getExpiredDate())
                .privateNote(note.isPrivNote())
                .build();
    }

    public static Note mapNoteEditDtoToNote(NoteEditDTO note) {
        return Note.builder()
                .id(note.getId())
                .uuid(note.getUuid())
                .title(note.getTitle())
                .content(note.getContent())
                .createdDate(note.getCreatedDate())
                .expiredDate(note.getExpiredDate())
                .privNote(note.isPrivateNote())
                .user(note.getUser())
                .build();
    }

    public static NoteDeleteDTO mapNoteToNoteDeleteDTO(Note note) {
        return NoteDeleteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    public static Note mapNotePrivateDtoToNote(NotePrivateDTO note) {
        return Note.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .user(note.getUser())
                .uuid(note.getUuid())
                .privNote(note.isPrivateNote())
                .build();
    }


    public static Note mapNoteTimeDtoToNote(NoteTimeDTO noteTimeDTO) {
        return Note.builder()
                .title(noteTimeDTO.getTitle())
                .content(noteTimeDTO.getContent())
                .user(noteTimeDTO.getUser())
                .uuid(noteTimeDTO.getUuid())
                .createdDate(noteTimeDTO.getCreatedDate())
                .expiredDate(noteTimeDTO.getExpiredDate())
                .build();
    }
}
