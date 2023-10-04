package com.lizardostrich.quoteandpolicymanagement.controller;

import lombok.Data;

import java.util.List;

@Data
public class QuoteRequest {
    private List<Long> policyIds;
    private boolean hasProvincialInsurance;
    private boolean wantsSpouseCoverage;
    private int age;
    private int spouseAge;
    private int numberOfDependentChildren;


    public QuoteRequest(List<Long> policyIds, boolean hasProvincialInsurance, boolean wantsSpouseCoverage, int age, int spouseAge, int numberOfDependentChildren) {
        this.policyIds = policyIds;
        this.hasProvincialInsurance = hasProvincialInsurance;
        this.wantsSpouseCoverage = wantsSpouseCoverage;
        this.age = age;
        this.spouseAge = spouseAge;
        this.numberOfDependentChildren = numberOfDependentChildren;
    }

    public QuoteRequest() {
    }
}
