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

        double totalPremium = 0.0;

        //user individual premium calculation
        List<Policy> selectedPolicies = policyRepository.findAllById(policyIds);
        double userPremium = getCustomerPremium(selectedPolicies,age);
        totalPremium += userPremium;

        //spouse premium calculation
        if(wantsSpouseCoverage){
            List<Long> spousePolicyIds = quoteRequest.getSpousePolicyIds();
            selectedPolicies = policyRepository.findAllById(spousePolicyIds);
            double spousePremium = getCustomerPremium(selectedPolicies,spouseAge);
            totalPremium += spousePremium;
        }

        //dependent premium calculation
        if(numberOfDependentChildren>0){
            List<Long> dependentPolicyIds = quoteRequest.getDependentPolicyIds();
            selectedPolicies = policyRepository.findAllById(dependentPolicyIds);
            double dependentPremium = getDependentPremium(selectedPolicies,numberOfDependentChildren);
            totalPremium += dependentPremium;
        }

        return new ResponseEntity<>(totalPremium,HttpStatus.ACCEPTED);
    }

    private double getDependentPremium(List<Policy> selectedPolicies, int numberOfDependentChildren) {
        double dependentPremium = 0.0;
        for(Policy policy:selectedPolicies){
            dependentPremium = (policy.getPremium() / 2) * numberOfDependentChildren;
        }
        return dependentPremium;
    }

    private double getCustomerPremium(List<Policy> selectedPolicies, int age) {
        double customerPremium = 0.0;
        for(Policy policy:selectedPolicies){
            if(age>30){
                customerPremium += policy.getPremium() + adjustPremiumBasedOnAge(age, policy.getLevel());
            }
            else {
                customerPremium += policy.getPremium();
            }
        }
        return customerPremium;
    }

    private double adjustPremiumBasedOnAge(int age, Level level) {
        int multiplicationFactor  = (age - 30)/10 + 1;
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
