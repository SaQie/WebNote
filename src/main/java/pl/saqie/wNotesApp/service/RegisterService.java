package pl.saqie.wNotesApp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.saqie.wNotesApp.config.PasswordEncoder;
import pl.saqie.wNotesApp.domain.User;
import pl.saqie.wNotesApp.dto.auth.RegisterDTO;
import pl.saqie.wNotesApp.exceptions.*;
import pl.saqie.wNotesApp.mapper.RegisterDtoMapper;
import pl.saqie.wNotesApp.repository.UserRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public void registerNewUser(RegisterDTO registerDTO) throws PasswordDoNotMatch, UsernameAlreadyExistsException, EmailAlreadyExistsException, MessagingException, UnsupportedEncodingException {
        if (checkPasswordRepeatCorrect(registerDTO)) {
            if (checkUserExsists(registerDTO.getUsername(), registerDTO.getEmail())) {
                String token = generateUUID();
                emailService.sendEmail(registerDTO.getEmail(), token, "REGISTERUSER");
                User user = RegisterDtoMapper.mapToUser(registerDTO, token);
                user.setActivationTokenExpiredDate(LocalDateTime.now().plusMinutes(15));
                encodePasswordAndSaveToDB(user);
            }
        } else {
            throw new PasswordDoNotMatch("Hasła nie są takie same");
        }
    }

    public void enableUserAccount(String token) throws ActivationTimeExpiredException, UserNotFoundException {
        User user = userRepository.findByActivationToken(token);
        if (user != null) {
            if (checkActivationTime(user.getActivationTokenExpiredDate())) {
                user.setActivationToken(null);
                user.setActivationTokenExpiredDate(null);
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                userRepository.deleteById(user.getId());
                throw new ActivationTimeExpiredException("Czas aktywacji konta wygasł, zarejestruj się ponownie");
            }
        } else {
            throw new UserNotFoundException("Wystąpił błąd, nie odnaleziono tokenu aktywacyjnego.");
        }
    }

    private boolean checkActivationTime(LocalDateTime userTime) {
        return LocalDateTime.now().isBefore(userTime);
    }

    private boolean checkUserExsists(String username, String email) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (!userRepository.existsByEmail(email)) {
            if (!userRepository.existsByUsername(username)) {
                return true;
            }
            throw new UsernameAlreadyExistsException("Nazwa użytkownika " + username + " już istnieje.");
        }
        throw new EmailAlreadyExistsException("Email " + email + " już istnieje");
    }

    private boolean checkPasswordRepeatCorrect(RegisterDTO registerDTO) {
        return registerDTO.getPassword().equals(registerDTO.getPasswordRepeat());
    }

    private void encodePasswordAndSaveToDB(User user) {
        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedDate(LocalDateTime.now());
        userRepository.save(user);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
