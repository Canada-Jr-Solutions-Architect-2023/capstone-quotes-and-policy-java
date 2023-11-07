package com.lizardostrich.quoteandpolicymanagement.testUtils;

import com.lizardostrich.quoteandpolicymanagement.model.Payment;
import com.lizardostrich.quoteandpolicymanagement.model.PolicyEnrollment;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class EnrollmentUtility {

    static java.sql.Date date = new java.sql.Date(2023,05,05);
    static java.sql.Date endDate = new java.sql.Date(2024,05,05);
    private static PolicyEnrollment policyEnrollment = new PolicyEnrollment(1L,
            "usertest@gmail.com",
            "Pankti Vyas",
            100.0,
            Payment.PENDING,
            date,
            endDate,
            Set.of(PolicyUtility.getPolicies().get(1), PolicyUtility.getPolicies().get(2)),
            Set.of(PolicyUtility.getPolicies().get(1), PolicyUtility.getPolicies().get(2)),
            Set.of(PolicyUtility.getPolicies().get(1)));

    public static PolicyEnrollment getPolicyEnrollment() {
        return policyEnrollment;
    }

    public static void setPolicyEnrollment(PolicyEnrollment policyEnrollment) {
        EnrollmentUtility.policyEnrollment = policyEnrollment;
    }
}
