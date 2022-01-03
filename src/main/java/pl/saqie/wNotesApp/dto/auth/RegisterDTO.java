package pl.saqie.wNotesApp.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {

    @Size(min = 5, max = 30, message = "Nazwa użytkownika powinna zawierać co najmniej 5 znaków.")
    private String username;
    @Size(min = 5, message = "Hasło powinno zawierać co najmniej 5 znaków.")
    private String password;
    private String passwordRepeat;
    @Email(message = "Wprowadz poprawny adres E-Mail.")
    @NotEmpty(message = "Email nie moze byc pusty.")
    private String email;
}
