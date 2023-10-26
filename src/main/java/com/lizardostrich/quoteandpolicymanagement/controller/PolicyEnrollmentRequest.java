package com.lizardostrich.quoteandpolicymanagement.controller;

import com.lizardostrich.quoteandpolicymanagement.model.Dependent;
import com.lizardostrich.quoteandpolicymanagement.model.Spouse;
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
    private Spouse spouse;
    private double premium;
    private List<Dependent> dependents;

}
