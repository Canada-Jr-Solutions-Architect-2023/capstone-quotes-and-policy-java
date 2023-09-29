package com.lizardostrich.quoteandpolicymanagement.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lizardostrich.quoteandpolicymanagement.controller.PolicyController;
import com.lizardostrich.quoteandpolicymanagement.model.*;
import com.lizardostrich.quoteandpolicymanagement.service.PolicyService;
import com.lizardostrich.quoteandpolicymanagement.testUtils.PolicyUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PolicyController.class)
public class PolicyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private PolicyService policyService;


    @Test
    public void getAllPolicies_shouldReturnAllPolicies() throws Exception {
        List<Policy> mockPolicies = PolicyUtility.getPolicies();

        when(policyService.getAllPolicies()).thenReturn(mockPolicies);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/policy/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(mockPolicies.get(0).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].level").value("STARTER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(mockPolicies.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].coverage").value(mockPolicies.get(0).getCoverage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].premium").value(mockPolicies.get(0).getPremium()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value(mockPolicies.get(2).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].level").value("ADVANCED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].description").value(mockPolicies.get(2).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].coverage").value(mockPolicies.get(2).getCoverage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].premium").value(mockPolicies.get(2).getPremium()));
    }

    @Test
    public void savePolicy_shouldReturnSavedPolicy() throws Exception {
        Policy policy = PolicyUtility.getPolicy();
        when(policyService.savePolicy(policy)).thenReturn(policy);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(policy.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value("STARTER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(policy.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coverage").value(policy.getCoverage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.premium").value(policy.getPremium()));

        verify(policyService,times(1)).savePolicy(any(Policy.class));
    }

    @Test
    public void getPolicyById_shouldReturnMatchedPolicy() throws Exception{
        Policy policy = PolicyUtility.getPolicy();
        Long policyId = 1L;
        when(policyService.getPolicyById(policyId)).thenReturn(policy);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/policy/{id}",policyId))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(policy.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value("STARTER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(policy.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coverage").value(policy.getCoverage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.premium").value(policy.getPremium()));

        verify(policyService,times(2)).getPolicyById(any(Long.class));
    }

    @Test
    public void getPolicyById_policyNotFound() throws Exception{
        Policy policy = PolicyUtility.getPolicy();
        Long policyId = policy.getId();
        when(policyService.getPolicyById(policyId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/policy/{id}",policyId))
                .andExpect(status().isNotFound());

        verify(policyService,times(1)).getPolicyById(any(Long.class));
    }

    @Test
    public void updatePolicy_shouldReturnUpdatedPolicy() throws Exception {
        Policy policy = PolicyUtility.getPolicy();

        when(policyService.getPolicyById(policy.getId())).thenReturn(policy);
        when(policyService.updatePolicy(policy)).thenReturn(policy);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(policy.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(policy.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value("STARTER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(policy.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coverage").value(policy.getCoverage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.premium").value(policy.getPremium()));

        verify(policyService,times(1)).getPolicyById(any(Long.class));
        verify(policyService,times(1)).updatePolicy(any(Policy.class));
    }

    @Test
    public void updatePolicy_InvalidId() throws Exception {
        Policy policy = PolicyUtility.getPolicy();
        policy.setId(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePolicy_PolicyNotFound() throws Exception {
        Policy policy = PolicyUtility.getPolicy();
        policy.setId(9L);

        when(policyService.getPolicyById(policy.getId())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isNotFound());

        verify(policyService,times(1)).getPolicyById(any(Long.class));
    }

    @Test
    public void deletePolicy_shouldDeletePolicy() throws Exception {
        when(policyService.deletePolicyById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/policy/{id}",1L)).andExpect(status().isAccepted());
        verify(policyService,times(1)).deletePolicyById(any(Long.class));
    }

    @Test
    public void deletePolicy_policyNotFound() throws Exception {
        when(policyService.deletePolicyById(1L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/policy/{id}",1L)).andExpect(status().isNotFound());
        verify(policyService,times(1)).deletePolicyById(any(Long.class));
    }
}
