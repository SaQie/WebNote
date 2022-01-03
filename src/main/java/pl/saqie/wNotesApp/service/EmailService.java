package pl.saqie.wNotesApp.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.saqie.wNotesApp.config.UrlProperties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class EmailService {

    private final UrlProperties urlProperties;
    private JavaMailSender javaMailSender;

    public void sendEmail(String email, String token, String option) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        String subject;
        String content;
        String link;
        switch (option) {
            case "FORGOTPASSWORD":
                link = urlProperties.getApplicationAdress() + "reset_password?token=" + token;
                helper.setFrom("Admin@Wnotes.pl", "Zapomniales hasla ?");
                helper.setTo(email);
                subject = "Link do zresetowania hasła";
                content = createForgorPasswordContentMessage(link);
                helper.setSubject(subject);
                helper.setText(content, true);
                javaMailSender.send(mimeMessage);
                break;
            case "REGISTERUSER":
                link = urlProperties.getApplicationAdress() + "activation?token=" + token;
                helper.setFrom("Admin@Wnotes.pl", "Rejestracja w wNotes");
                helper.setTo(email);
                subject = "Link do aktywacji konta wNotes";
                content = createRegisterUserContentMessage(link);
                helper.setSubject(subject);
                helper.setText(content, true);
                javaMailSender.send(mimeMessage);
                break;
        }

    }

    private String createForgorPasswordContentMessage(String link) {
        return "<p>Witaj,</p>"
                + "<p> zarządałeś linku do zresetowania hasła, dlatego wysyłamy Ci tą wiadomość </p>"
                + "<p> Kliknij w link poniżej i postępuj zgodnie z instrukcjami na ekranie aby zresetować hasło </p>"
                + "<p><a href=\"" + link + "\"> Zresetuj hasło </a></p>"
                + "<br>"
                + "<p>Jeśli to nie Ty zarządałeś zmiany hasła proszę zignoruj tą wiadmość.</p>";
    }

    private String createRegisterUserContentMessage(String link) {
        return "<p>Witaj,</p>"
                + "<p> Dziękujemy za założenie konta w serwisie wNotes </p>"
                + "<p> Kliknij w link poniżej i postępuj zgodnie z instrukcjami na ekranie aby potwierdzic rejestracje konta, link będzie aktywny przez 15 minut.</p>"
                + "<p><a href=\"" + link + "\"> Aktywuj konto </a></p>"
                + "<br>";
    }

}
