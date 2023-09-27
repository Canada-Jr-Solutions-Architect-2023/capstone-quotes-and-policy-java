package com.lizardostrich.quoteandpolicymanagement.serviceTests;

import com.lizardostrich.quoteandpolicymanagement.model.Policy;
import com.lizardostrich.quoteandpolicymanagement.repository.PolicyRepository;
import com.lizardostrich.quoteandpolicymanagement.service.PolicyService;
import com.lizardostrich.quoteandpolicymanagement.testUtils.PolicyUtility;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PolicyServiceTest {
    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private PolicyService policyService;

    @Test
    public void getAllPolicies_shouldReturnAllPolicies(){
        List<Policy> mockPolicies = PolicyUtility.getPolicies();
        when(policyRepository.findAll()).thenReturn(mockPolicies);

        List<Policy> policies = policyService.getAllPolicies();

        assertAll(
                ()-> assertEquals(mockPolicies.size(),policies.size()),
                ()-> assertEquals(mockPolicies.get(0),policies.get(0)),
                ()-> assertEquals(mockPolicies.get(1),policies.get(1)),
                ()-> assertEquals(mockPolicies.get(2),policies.get(2))
        );
    }

    @Test
    public void savePolicy_shouldReturnSavedPolicy(){
        Policy policy = PolicyUtility.getPolicy();
        when(policyRepository.save(policy)).thenReturn(policy);

        Policy savedPolicy = policyService.savePolicy(policy);

        assertEquals(policy,savedPolicy);
        verify(policyRepository,times(1)).save(policy);
    }

    @Test
    public void getPolicyById_shouldReturnMatchedPolicy(){
        Policy policy = PolicyUtility.getPolicy();
        Long policyId = 1L;
        when(policyRepository.findById(policyId)).thenReturn(Optional.ofNullable(policy));

        Policy matchedPolicy = policyService.getPolicyById(policyId);

        assertEquals(policy,matchedPolicy);
        verify(policyRepository,times(1)).findById(policyId);
    }

    @Test
    public void updatePolicy_shouldReturnUpdatedPolicy(){
        Policy policy = PolicyUtility.getPolicies().get(0);

        when(policyRepository.findById(policy.getId())).thenReturn(Optional.of(policy));
        when(policyRepository.save(policy)).thenReturn(policy);

        Policy updatedPolicy = policyService.updatePolicy(policy);

        assertNotNull(updatedPolicy);
        assertEquals(policy,updatedPolicy);
    }
}
