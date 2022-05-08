package com.projectx.customer.service;

import com.projectx.clients.fraud.FraudCheckResponse;
import com.projectx.clients.fraud.FraudClient;
import com.projectx.clients.notification.NotificationClient;
import com.projectx.clients.notification.NotificationRequest;
import com.projectx.customer.controller.CustomerRegistrationRequest;
import com.projectx.customer.model.Customer;
import com.projectx.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (Objects.requireNonNull(fraudCheckResponse).isFraudster()) {
            throw new IllegalStateException("Customer verified as fraudster");
        }

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to ProjectX...",
                                customer.getFirstName())
                )
        );

    }
}
