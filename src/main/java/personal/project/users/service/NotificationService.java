package personal.project.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import personal.project.users.domain.dto.MailDTO;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;

    public String sendMail(MailDTO mailDTO) {
        try {
            SimpleMailMessage message  = new SimpleMailMessage();

            message.setTo(mailDTO.destinyMail());
            message.setSubject(mailDTO.subject());
            message.setText(mailDTO.body());
            message.setFrom("guitarrasgabi@gmail.com");

            mailSender.send(message);

            return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());

            return "Erro ao enviar o e-mail: " + e.getMessage();
        }
    }
}
