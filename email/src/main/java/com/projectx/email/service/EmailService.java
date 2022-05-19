package com.projectx.email.service;

import com.projectx.email.enums.EmailStatus;
import com.projectx.email.model.EmailModel;
import com.projectx.email.respository.EmailRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@Data
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(EmailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    public void sendEmail(@Valid EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getEmailSubject());
            message.setText(emailModel.getText());
            mailSender.send(message);
            emailModel.setEmailStatus(EmailStatus.SENT);
            log.info("Email was sent successful");
            emailRepository.save(emailModel);
        } catch (Exception e) {
            emailModel.setEmailStatus(EmailStatus.ERROR);
            log.error("Email was not sent error: {}", e.getMessage());
            throw new IllegalStateException();
        }
    }
}
