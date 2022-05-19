package com.projectx.clients.email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "email")
public interface EmailClient {

    @PostMapping("api/v1/send-email")
    void sendEmail(EmailRequest EmailRequest);
}
