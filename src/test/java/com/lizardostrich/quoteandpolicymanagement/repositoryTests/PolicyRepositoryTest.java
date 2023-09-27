package com.lizardostrich.quoteandpolicymanagement.repositoryTests;

import com.lizardostrich.quoteandpolicymanagement.model.Level;
import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import com.lizardostrich.quoteandpolicymanagement.testUtils.PolicyUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //Ensures each test runs in isolation and starts with a clean database state.
public class PolicyRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PolicyRepository policyRepository;

    @BeforeEach
    public void initEach(){

    }
    @Test
    public void findAll_shouldReturnAllPolicies(){
        List<Policy> mockPolicies = PolicyUtility.getPolicies();
        mockPolicies.forEach(testEntityManager::merge);

        List<Policy> policies = policyRepository.findAll();

        assertAll(
                ()-> assertEquals(mockPolicies.size(),policies.size()),
                ()-> assertEquals(mockPolicies.get(0),policies.get(0)),
                ()-> assertEquals(mockPolicies.get(1),policies.get(1)),
                ()-> assertEquals(mockPolicies.get(2),policies.get(2))
        );
    }

    @Test
    public void save_SavesPolicyInDb(){
        Policy policy = new Policy("title", Level.STARTER,"DESC",100,10);
        Policy savedPolicy = testEntityManager.persistAndFlush(policy);
        assertNotNull(savedPolicy.getId());
    }

    @Test
    public void getPolicyById_shouldReturnMatchedPolicy(){
        Policy policy = new Policy("title", Level.STARTER,"DESC",100,10);
        Long policyId = 1L;
        testEntityManager.persist(policy);

        Optional<Policy> matchedPolicy = policyRepository.findById(policyId);
        assertTrue(matchedPolicy.isPresent());
        assertEquals(policy,matchedPolicy.get());
    }

    @Test
    public void updatePolicy_shouldUpdateInDb(){
        Policy existingPolicy = PolicyUtility.getPolicy();
        existingPolicy = testEntityManager.merge(existingPolicy);

        existingPolicy.setTitle("Updated Title");
        existingPolicy.setCoverage(50000);

        Policy updatedPolicy = policyRepository.save(existingPolicy);

        Policy retrievedPolicy = testEntityManager.find(Policy.class,1L);

        assertEquals("Updated Title",retrievedPolicy.getTitle());
        assertEquals(50000,retrievedPolicy.getCoverage());
    }
}
