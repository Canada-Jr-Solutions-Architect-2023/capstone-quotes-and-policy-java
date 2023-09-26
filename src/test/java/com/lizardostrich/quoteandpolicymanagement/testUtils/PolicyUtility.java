package com.lizardostrich.quoteandpolicymanagement.testUtils;

import com.lizardostrich.quoteandpolicymanagement.model.Level;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolicyUtility {
    public static List<Policy> getPolicies() {
        return policies;
    }

    private static List<Policy> policies = new ArrayList<>(Arrays.asList(
            new Policy("Vision", Level.STARTER, "DESC", 10000, 10),
            new Policy("Vision2", Level.STARTER, "DESC", 10000, 10)
    ));
}
