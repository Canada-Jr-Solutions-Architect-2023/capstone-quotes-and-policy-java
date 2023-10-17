package com.lizardostrich.quoteandpolicymanagement.controller;

import com.lizardostrich.quoteandpolicymanagement.feign.CustomerServiceProxy;
import com.lizardostrich.quoteandpolicymanagement.model.Customer;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {
    @Autowired
    private PolicyService policyService;
    @Autowired
    private CustomerServiceProxy customerServiceProxy;

    public PolicyController(PolicyService policyService, CustomerServiceProxy customerServiceProxy) {
        this.policyService = policyService;
        this.customerServiceProxy = customerServiceProxy;
    }

    public PolicyController() {
    }

    @GetMapping("/all")
    public List<Policy> getAllPolicies(){
        return this.policyService.getAllPolicies();
    }
    @PostMapping
    public ResponseEntity<Policy> savePolicy(@RequestBody Policy policy){
        return new ResponseEntity<Policy>(policyService.savePolicy(policy), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Policy> updatePolicy(@RequestBody Policy policy){
        if(policy.getId() == null){
            return new ResponseEntity("Entity requires Id for update",HttpStatus.BAD_REQUEST);
        } else if (this.policyService.getPolicyById(policy.getId()) == null) {
            return new ResponseEntity("Entity not found",HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity(this.policyService.updatePolicy(policy), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable int id){
        if (this.policyService.getPolicyById((long) id) == null) {
            return new ResponseEntity("Entity not found",HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity(policyService.getPolicyById(Long.valueOf(id)),HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Policy> deletePolicy(@PathVariable int id){
        Boolean deleted = policyService.deletePolicyById(Long.valueOf(id));

        if(deleted){
            return new ResponseEntity("Policy deleted successfully", HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity("Policy not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCustomerById/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id){
        Customer customer = customerServiceProxy.getCustomerById(id);
        return customer;
    }

    @GetMapping("/feignTest")
    public String FeignTest(){
        return "Feign ok!";
    }
}
