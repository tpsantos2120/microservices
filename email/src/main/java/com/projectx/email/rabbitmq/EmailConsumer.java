package com.projectx.email.rabbitmq;

import com.projectx.clients.email.EmailRequest;
import com.projectx.email.model.EmailModel;
import com.projectx.email.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.email-notification}")
    public void consumer(EmailRequest emailRequest) {
        log.info("Consumed {} from queue", emailRequest);
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRequest, emailModel);
        emailService.sendEmail(emailModel);
    }

}
