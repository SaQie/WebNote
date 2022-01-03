package pl.saqie.wNotesApp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.saqie.wNotesApp.config.PasswordEncoder;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.auth.ChangePasswordDTO;
import pl.saqie.wNotesApp.dto.auth.ResetPasswordDTO;
import pl.saqie.wNotesApp.exceptions.PasswordDoNotMatch;
import pl.saqie.wNotesApp.exceptions.TokenNotFoundException;
import pl.saqie.wNotesApp.exceptions.UserNotFoundException;
import pl.saqie.wNotesApp.mapper.RegisterDtoMapper;
import pl.saqie.wNotesApp.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void updatePassword(ResetPasswordDTO resetPasswordDTO) throws UserNotFoundException, PasswordDoNotMatch {
        if (resetPasswordDTO.getPassword().equals(resetPasswordDTO.getPasswordRepeat())) {
            String encodedNewPassword = passwordEncoder.bCryptPasswordEncoder().encode(resetPasswordDTO.getPassword());
            Optional<User> userOptional = userRepository.findById(resetPasswordDTO.getId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPassword(encodedNewPassword);
                user.setResetPasswordToken(null);
                userRepository.save(user);
            } else {
                throw new UserNotFoundException("Wystąpił błąd, skontaktuj się z administratorem");
            }
        } else {
            throw new PasswordDoNotMatch("Hasła nie są zgodne !");
        }

    }

    public String updateResetPasswordTokenToUser(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String randomToken = getRandomToken();
            user.setResetPasswordToken(randomToken);
            userRepository.save(user);
            return randomToken;
        } else {
            throw new UserNotFoundException("Nie odnaleziono użytkownika na podstawie adresu E-mail " + email);
        }
    }

    private String getRandomToken() {
        return UUID.randomUUID().toString();
    }

    public ResetPasswordDTO getUserByResetPasswordToken(String token) throws TokenNotFoundException {
        User user = userRepository.findByResetPasswordToken(token);
        if (user != null) {
            return RegisterDtoMapper.mapToResetPasswordDTO(user);
        }
        throw new TokenNotFoundException("Token " + token + " jest nieprawidłowy");
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) throws PasswordDoNotMatch, UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(changePasswordDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (checkPasswordCorrect(changePasswordDTO, user)) {
                String encodedNewPassword = passwordEncoder.bCryptPasswordEncoder().encode(changePasswordDTO.getNewPassword());
                user.setPassword(encodedNewPassword);
                userRepository.save(user);
            }
        } else {
            throw new UserNotFoundException("Wystąpił błąd skontatkuj się z administratorem strony.");
        }
    }

    private boolean checkPasswordCorrect(ChangePasswordDTO changePasswordDTO, User user) throws PasswordDoNotMatch {
        if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getNewPasswordRepeat())) {
            if (passwordEncoder.bCryptPasswordEncoder().matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
                return true;
            } else {
                throw new PasswordDoNotMatch("Aktualne hasło jest nieprawidłowe.");
            }
        } else {
            throw new PasswordDoNotMatch("Nowe hasła nie są takie same.");
        }
    }
}
