package com.projectx.customer.service;

import com.projectx.amqp.component.RabbitMQMessageProducer;
import com.projectx.clients.email.EmailRequest;
import com.projectx.clients.fraud.FraudCheckResponse;
import com.projectx.clients.fraud.FraudClient;
import com.projectx.clients.notification.NotificationRequest;
import com.projectx.customer.model.CustomerModel;
import com.projectx.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;


    public CustomerModel registerCustomer(CustomerModel customerModel) {

        CustomerModel addedCustomer = customerRepository.saveAndFlush(customerModel);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customerModel.getId());

        if (Objects.requireNonNull(fraudCheckResponse).isFraudster()) {
            throw new IllegalStateException("Customer verified as fraudster");
        }

        rabbitMQMessageProducer.publish(
                new NotificationRequest(
                        customerModel.getPhoneNumber(),
                        customerModel.getFirstName(),
                        customerModel.getLastName()
                ),
                "internal.exchange",
                "internal.notification.routing-key"
        );

        rabbitMQMessageProducer.sendEmail(
                new EmailRequest(
                        customerModel.getFirstName(),
                        customerModel.getFirstName(),
                        customerModel.getEmail()),
                "internal.exchange",
                "internal.email.notification.routing-key");

        return addedCustomer;
    }
}
