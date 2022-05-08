package com.projectx.fraud.controller;

import com.projectx.clients.fraud.FraudCheckResponse;
import com.projectx.fraud.service.FraudCheckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerID) {
        boolean isFraudulentCustomer =
                fraudCheckService.isFraudulentCustomer(customerID);
        log.info("Fraud check request for customer {}", customerID);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

}