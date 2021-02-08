package de.schichttauschen.web.service;

import de.schichttauschen.web.data.entity.Account;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailSenderService {
    private final JavaMailSender emailSender;
    private final Logger log = LoggerFactory.getLogger(EmailSenderService.class);

    @Value("${app.mail.from}")
    private String mailFrom;

    @Value("${spring.mail.host}")
    private String smtpHost;

    private boolean isMailEnabled() {
        if (smtpHost == null || smtpHost.equals("null")) {
            log.warn("Mail sending is not enabled!");
            return false;
        } else return true;
    }

    private boolean isMailDisabled() {
        return !isMailEnabled();
    }

    public boolean sendAccountActivationMail(Account account) {
        if (isMailDisabled()) return true;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(account.getEmail());
        message.setSubject("Please activate your account");
        message.setText("Activation link: foobarbaz");
        emailSender.send(message);
        return true;
    }
}
