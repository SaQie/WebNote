package pl.saqie.wNotesApp.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPasswordDTO {

    @Email(message = "Wprowadz poprawny adres E-mail")
    @NotEmpty(message = "E-mail nie moze byc pusty")
    private String email;
}
