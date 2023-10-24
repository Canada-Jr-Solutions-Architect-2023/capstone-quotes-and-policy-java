package com.lizardostrich.quoteandpolicymanagement.service;

import com.lizardostrich.quoteandpolicymanagement.controller.PolicyEnrollmentRequest;
import com.lizardostrich.quoteandpolicymanagement.model.Payment;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.model.PolicyEnrollment;
import com.lizardostrich.quoteandpolicymanagement.repository.EnrollmentRepository;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        // test
        Set<Policy> policy_set = new HashSet<>();

        for (Long pId : request.getUserPolicyIds()) {
            Policy p = getPolicyById(pId);
            policy_set.add(p);
        }

        policyEnrollment.setPaymentStatus(Payment.PENDING);
        policyEnrollment.setPrimaryUserPolicies(policy_set);

        Set<Policy> spouse_set = new HashSet<>();
        for(Long pID: request.getSpousePolicyIds()){
            Policy p = getPolicyById(pID);
            spouse_set.add(p);
        }
        policyEnrollment.setSpousePolicies(spouse_set);
        enrollmentRepository.save(policyEnrollment);
        return "Enrollment successful!";
    }

    public Optional<PolicyEnrollment> getPolicyEnrollment(Long id){
        return enrollmentRepository.findById(id);
    }

    public Set<Policy> getPrimaryUserPolicyByEnrollment(Long id){
        PolicyEnrollment policyEnrollment = enrollmentRepository.findById(id).orElse(null);
        return policyEnrollment.getPrimaryUserPolicies();
    }
}
