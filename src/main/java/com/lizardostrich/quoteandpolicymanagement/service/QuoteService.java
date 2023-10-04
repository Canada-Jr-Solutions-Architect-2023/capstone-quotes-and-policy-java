package com.lizardostrich.quoteandpolicymanagement.service;

import com.lizardostrich.quoteandpolicymanagement.controller.QuoteRequest;
import com.lizardostrich.quoteandpolicymanagement.model.Level;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private PolicyRepository policyRepository;
    public ResponseEntity<Double> generateQuote(QuoteRequest quoteRequest) {
        System.out.println(quoteRequest);
        List<Long> policyIds = quoteRequest.getPolicyIds();

        Boolean hasProvincialInsurance = quoteRequest.isHasProvincialInsurance();
        Boolean wantsSpouseCoverage = quoteRequest.isWantsSpouseCoverage();
        int age = quoteRequest.getAge();
        int spouseAge = quoteRequest.getSpouseAge();
        int numberOfDependentChildren = quoteRequest.getNumberOfDependentChildren();
        
        List<Policy> selectedPolicies = policyRepository.findAllById(policyIds);
        
        double totalPremium = 0.0;
        double policyPremium = 0.0;
        for(Policy policy:selectedPolicies){
            if(age>30 || spouseAge>30){
                policyPremium += policy.getPremium() + adjustPremiumBasedOnAge(age,spouseAge, policy.getLevel());
            }
            else {
                policyPremium += policy.getPremium();
            }
        }
        return new ResponseEntity<>(policyPremium,HttpStatus.ACCEPTED);
    }

    private double adjustPremiumBasedOnAge(int age, int spouseAge, Level level) {
        int maxAge = Math.max(age,spouseAge);
        int multiplicationFactor  = (maxAge - 30)/10 + 1;
        int increasePerAgeBracketStarter = 5;
        int increasePerAgeBracketEssential = 7;
        int increasePerAgeBracketAdvanced = 8;
        switch (level){
            case STARTER :
               return increasePerAgeBracketStarter * multiplicationFactor;
            case ESSENTIAL:
                return increasePerAgeBracketEssential * multiplicationFactor;
            case ADVANCED:
                return increasePerAgeBracketAdvanced * multiplicationFactor;
        }
        return 0;
    }
}
