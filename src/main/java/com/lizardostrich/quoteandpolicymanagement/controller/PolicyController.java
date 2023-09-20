package com.lizardostrich.quoteandpolicymanagement.controller;

import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.service.PolicyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {
    private PolicyService policyService;

    public PolicyController(PolicyService policyService){
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<Policy> savePolicy(@RequestBody Policy policy){
        return new ResponseEntity<Policy>(policyService.savePolicy(policy), HttpStatus.CREATED);
    }
}
