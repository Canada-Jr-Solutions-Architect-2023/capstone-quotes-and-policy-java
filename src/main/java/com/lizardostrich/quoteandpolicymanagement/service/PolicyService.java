package com.lizardostrich.quoteandpolicymanagement.service;

import com.lizardostrich.quoteandpolicymanagement.controller.PaymentRequest;
import com.lizardostrich.quoteandpolicymanagement.controller.PolicyEnrollmentRequest;
import com.lizardostrich.quoteandpolicymanagement.model.*;
import com.lizardostrich.quoteandpolicymanagement.repository.DependentRepository;
import com.lizardostrich.quoteandpolicymanagement.repository.EnrollmentRepository;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import com.lizardostrich.quoteandpolicymanagement.repository.SpouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class PolicyService {
    public PolicyService(PolicyRepository policyRepository, EnrollmentRepository enrollmentRepository) {
        this.policyRepository = policyRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private SpouseRepository spouseRepository;
    @Autowired
    private DependentRepository dependentRepository;

    public PolicyService(PolicyRepository policyRepository){
        this.policyRepository = policyRepository;
    }

    public PolicyService() {
    }

    public Policy savePolicy(Policy policy){
        return policyRepository.save(policy);
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElse(null);
    }

    public Policy updatePolicy(Policy policy) {
        Policy existingPolicy = policyRepository.findById(policy.getId()).orElseThrow(()->new EntityNotFoundException("Policy not found."));

        existingPolicy.setTitle(policy.getTitle());
        existingPolicy.setLevel(policy.getLevel());
        existingPolicy.setDescription(policy.getDescription());
        existingPolicy.setCoverage(policy.getCoverage());
        existingPolicy.setPremium(policy.getPremium());

        return policyRepository.save(existingPolicy);
    }

    public Boolean deletePolicyById(Long id) {
        if(policyRepository.existsById(id)){
            policyRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    public String enrollCustomer(PolicyEnrollmentRequest request) {
        PolicyEnrollment policyEnrollment = new PolicyEnrollment();

        // Set primary user policies
        Set<Policy> policy_set = new HashSet<>();
        for (Long pId : request.getUserPolicyIds()) {
            Policy p = getPolicyById(pId);
            policy_set.add(p);
        }
        policyEnrollment.setPrimaryUserPolicies(policy_set);

        //Set spouse policies
        Set<Policy> spouse_set = new HashSet<>();
        for(Long pID: request.getSpousePolicyIds()){
            Policy p = getPolicyById(pID);
            spouse_set.add(p);
        }
        policyEnrollment.setSpousePolicies(spouse_set);

        //Set dependent policies
        Set<Policy> dependent_set = new HashSet<>();
        for(Long pID: request.getDependentPolicyIds()){
            Policy p = getPolicyById(pID);
            dependent_set.add(p);
        }
        policyEnrollment.setDependentPolicies(dependent_set);

        //Setting other fields
        policyEnrollment.setFullName(request.getFullname());
        Date date = Date.valueOf(LocalDate.now());
        policyEnrollment.setStartDate(date);
        policyEnrollment.setCustomerEmail(GetUserEmail());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        policyEnrollment.setEndDate(calendar.getTime());

        policyEnrollment.setPaymentStatus(Payment.PENDING);
        policyEnrollment.setPremium(request.getPremium());
        enrollmentRepository.save(policyEnrollment);

        //Save spouse in spouse table
        Spouse spouse = request.getSpouse();
        spouse.setPolicyEnrollment(policyEnrollment);
        spouseRepository.save(spouse);

        for(Dependent dependent : request.getDependents()){
            dependent.setPolicyEnrollment(policyEnrollment);
            dependentRepository.save(dependent);
        }


        return "Enrollment successful!";
    }

    public Optional<PolicyEnrollment> getPolicyEnrollment(Long id){
        return enrollmentRepository.findById(id);
    }

    public Set<Policy> getPrimaryUserPolicyByEnrollment(){
        PolicyEnrollment policyEnrollment = enrollmentRepository.findByCustomerEmail(GetUserEmail()).get(0);
        //PolicyEnrollment policyEnrollment = enrollmentRepository.findById(id).orElse(null);
        return policyEnrollment.getPrimaryUserPolicies();
    }

    public Set<Policy> getCurrentUserSpousePolicyByEnrollment() {
        PolicyEnrollment policyEnrollment = enrollmentRepository.findByCustomerEmail(GetUserEmail()).get(0);
        return policyEnrollment.getSpousePolicies();
    }

    public Set<Policy> getCurrentUserDependentPolicyByEnrollment() {
        PolicyEnrollment policyEnrollment = enrollmentRepository.findByCustomerEmail(GetUserEmail()).get(0);
        return policyEnrollment.getDependentPolicies();
    }
    public Optional<PolicyEnrollment> getCurrentUserPolicyEnrollment(){
        return Optional.ofNullable(enrollmentRepository.findByCustomerEmail(GetUserEmail()).get(0));
    }

    public Double getPremiumForPayment(Long id) {
        PolicyEnrollment policyEnrollment = enrollmentRepository.findById(id).orElse(null);
        return policyEnrollment.getPremium();
    }

    public ResponseEntity<String> updatePayment(PaymentRequest request){
        System.out.println(request.getId());
        System.out.println(request.isPayment_status());
        return ResponseEntity.ok("Payment status updated!");
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
