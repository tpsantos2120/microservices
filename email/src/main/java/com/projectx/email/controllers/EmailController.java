package com.projectx.email.controllers;

import com.projectx.email.dto.EmailDTO;
import com.projectx.email.model.EmailModel;
import com.projectx.email.service.EmailService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Data
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("api/v1/send-email")
    public void sendEmail(@RequestBody @Valid EmailDTO emailDTO) {
        log.info("Sending email...");
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);
    }
}
