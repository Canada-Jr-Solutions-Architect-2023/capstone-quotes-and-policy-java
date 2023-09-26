package com.lizardostrich.quoteandpolicymanagement.repositoryTests;

import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import com.lizardostrich.quoteandpolicymanagement.testUtils.PolicyUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PolicyRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PolicyRepository policyRepository;

    @Test
    public void findAll_shouldReturnAllPolicies(){
        List<Policy> mockPolicies = PolicyUtility.getPolicies();
        mockPolicies.forEach(testEntityManager::persistAndFlush);
        testEntityManager.flush();

        List<Policy> policies = policyRepository.findAll();

        assertAll(
                ()-> assertEquals(mockPolicies.size(),policies.size()),
                ()-> assertEquals(mockPolicies.get(0),policies.get(0)),
                ()-> assertEquals(mockPolicies.get(1),policies.get(1)),
                ()-> assertEquals(mockPolicies.get(2),policies.get(2))
        );
    }
}
