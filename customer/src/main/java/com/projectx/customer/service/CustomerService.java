package com.projectx.customer.service;

import com.projectx.customer.controller.CustomerRegistrationRequest;
import com.projectx.customer.controller.FraudCheckResponse;
import com.projectx.customer.model.Customer;
import com.projectx.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                    FraudCheckResponse.class,
                    customer.getId());
        if (Objects.requireNonNull(fraudCheckResponse).isFraudster()) {
            throw new IllegalStateException("Customer verified as fraudster");
        }
    }
}
