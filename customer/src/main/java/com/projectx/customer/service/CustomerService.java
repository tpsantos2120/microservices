package com.projectx.customer.service;

import com.projectx.amqp.component.RabbitMQMessageProducer;
import com.projectx.clients.fraud.FraudCheckResponse;
import com.projectx.clients.fraud.FraudClient;
import com.projectx.clients.notification.NotificationRequest;
import com.projectx.customer.model.Customer;
import com.projectx.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;


    public Customer registerCustomer(Customer customer) {

        Customer addedCustomer = customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (Objects.requireNonNull(fraudCheckResponse).isFraudster()) {
            throw new IllegalStateException("Customer verified as fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to ProjectX...",
                        customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
        return addedCustomer;
    }
}
