package com.projectx.customer.controller;

import com.projectx.customer.dto.CustomerDTO;
import com.projectx.customer.model.Customer;
import com.projectx.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        log.info("Started customer registration {}", customerDTO);
        Customer customerModel = new Customer();
        BeanUtils.copyProperties(customerDTO, customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.registerCustomer(customerModel));
    }
}
