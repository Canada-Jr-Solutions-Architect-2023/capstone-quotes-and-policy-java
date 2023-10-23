package com.lizardostrich.quoteandpolicymanagement.controller;

import lombok.Data;
import java.util.List;

@Data
public class PolicyEnrollmentRequest {
    private String fullname;
    private List<Long> userPolicyIds;
    private List<Long> spousePolicyIds;
    private List<Long> dependentPolicyIds;
    private int ageOfPrimaryApplicant;
    private int numberOfDependents;
    private SpouseRequest spouse;
    private List<DependentRequest> dependents;

}
