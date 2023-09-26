package com.lizardostrich.quoteandpolicymanagement.controllerTests;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@WebMvcTest(PolicyController.class)
public class PolicyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PolicyService policyService;


    @Test
    public void getAllPolicies_shouldReturnAllPolicies() throws Exception {

        List<Policy> mockPolicies = PolicyUtility.getPolicies();

        when(policyService.getAllPolicies()).thenReturn(mockPolicies);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/policy/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
}
