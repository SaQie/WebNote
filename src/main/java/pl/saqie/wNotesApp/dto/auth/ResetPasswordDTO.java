package pl.saqie.wNotesApp.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDTO {

    private Long id;
    @Size(min = 5, message = "Hasło powinno zawierać co najmniej 5 znaków.")
    private String password;
    private String passwordRepeat;
    private String resetPasswordToken;
}
