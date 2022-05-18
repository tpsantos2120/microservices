package com.projectx.service;

import com.projectx.enums.EmailStatus;
import com.projectx.model.EmailModel;
import com.projectx.respository.EmailRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Data
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(EmailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getEmailSubject());
            message.setText(emailModel.getText());
            mailSender.send(message);
            emailModel.setEmailStatus(EmailStatus.SENT);
        } catch (Exception e) {
            emailModel.setEmailStatus(EmailStatus.ERROR);
        } finally {
            emailRepository.insert(emailModel);
        }
    }
}
