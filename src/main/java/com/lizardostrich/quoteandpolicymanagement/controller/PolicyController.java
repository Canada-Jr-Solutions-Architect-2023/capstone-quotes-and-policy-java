package com.lizardostrich.quoteandpolicymanagement.controller;

import com.lizardostrich.quoteandpolicymanagement.feign.CustomerServiceProxy;
import com.lizardostrich.quoteandpolicymanagement.model.AWSUser;
import com.lizardostrich.quoteandpolicymanagement.model.Customer;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.model.PolicyEnrollment;
import com.lizardostrich.quoteandpolicymanagement.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Policy> savePolicy(@RequestBody Policy policy){
        return new ResponseEntity<Policy>(policyService.savePolicy(policy), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
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

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
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

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollCustomer(@RequestBody PolicyEnrollmentRequest request){
        System.out.println(GetUserEmail());
        return new ResponseEntity(policyService.enrollCustomer(request),HttpStatus.OK);
    }

    @GetMapping("/getEnrollment/{id}")
    public Optional<PolicyEnrollment> getEnrollmentById(@PathVariable("id") Long id){
        Optional<PolicyEnrollment> policyEnrollment = policyService.getPolicyEnrollment(id);
        return policyEnrollment;
    }

    @GetMapping("/getPrimaryUserPolicies/{id}")
    public Set<Policy> getPrimaryUserPolicy(@PathVariable("id") Long id){
        return policyService.getPrimaryUserPolicyByEnrollment(id);
    }

    @GetMapping("/getPremiumForPayment/{id}")
    public Double getPremiumForPayment(@PathVariable("id") Long id){
        return policyService.getPremiumForPayment(id);
    }

    @PutMapping("/updatePaymentStatus")
    public ResponseEntity<String> updatePayment(@RequestBody PaymentRequest paymentRequest){
        return policyService.updatePayment(paymentRequest);

    }

    public String GetUserEmail(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        headers.add("Authorization","Bearer "+ principal.getTokenValue());
        ResponseEntity<AWSUser> response = restTemplate.exchange("https://customer-service.auth.us-east-2.amazoncognito.com/oauth2/userInfo", HttpMethod.GET,
                requestEntity, AWSUser.class);
        AWSUser user = response.getBody();
        return user.getEmail();
    }
}
