package sample.cafekiosk.spring.mail;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String title, String content) {

        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, title, content);
        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .title(title)
                    .content(content)
                    .build()
            );
            return true;
        }

        return false;
    }
}
