package com.lizardostrich.quoteandpolicymanagement.service;

import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {
    private PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository){
        this.policyRepository = policyRepository;
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
        return policyRepository.save(policy);
    }
}
