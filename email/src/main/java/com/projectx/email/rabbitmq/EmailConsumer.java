package com.projectx.email.rabbitmq;

import com.projectx.clients.email.EmailRequest;
import com.projectx.email.model.EmailModel;
import com.projectx.email.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
        emailModel.setEmailSubject("Nutrition App");
        emailModel.setEmailFrom("thiagocroza472@gmail.com");
        emailModel.setEmailTo(emailRequest.email());
        emailModel.setText("Thank you for joining our app!");
        emailModel.setOwnerRef(emailRequest.firstName() + " " + emailRequest.lastName());
        emailService.sendEmail(emailModel);
    }

}
