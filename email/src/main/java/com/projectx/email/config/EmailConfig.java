package com.projectx.email.config;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmailConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.email-notification}")
    private String emailNotificationQueue;

    @Value("${rabbitmq.routing-keys.internal-email-notification}")
    private String internalEmailNotificationRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue emailNotificationQueue() {
        return new Queue(this.emailNotificationQueue);
    }

    @Bean
    public Binding internalToNotification() {
        return BindingBuilder
                .bind(emailNotificationQueue())
                .to(internalTopicExchange())
                .with(this.internalEmailNotificationRoutingKey);
    }
}
