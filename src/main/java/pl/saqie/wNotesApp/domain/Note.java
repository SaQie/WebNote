package pl.saqie.wNotesApp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uuid;
    private String title;
    @Lob
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredDate;
    private boolean privNote;
    @ManyToOne
    private User user;
}
