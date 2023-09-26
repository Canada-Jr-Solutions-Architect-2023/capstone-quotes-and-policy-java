package com.lizardostrich.quoteandpolicymanagement.testUtils;

import com.lizardostrich.quoteandpolicymanagement.model.Level;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolicyUtility {

    public static Policy getPolicy() {
        return policy;
    }

    private static Policy policy = new Policy("Vision", Level.STARTER, "Description", 10000, 10);
    public static List<Policy> getPolicies() {
        return policies;
    }

    private static List<Policy> policies = new ArrayList<>(Arrays.asList(
            new Policy("Vision", Level.STARTER, "Description for vision starter", 500, 50),
            new Policy("Vision", Level.ESSENTIAL, "Description for vision essential", 10000, 100),
            new Policy("Vision2", Level.ADVANCED, "Description for vision advanced", 20000, 150)
    ));
}
