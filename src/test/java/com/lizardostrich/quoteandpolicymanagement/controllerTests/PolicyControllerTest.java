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
import java.util.logging.Level;

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

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/policy/all").contentType(MediaType.APPLICATION_JSON);

        List<Policy> mockPolicies = PolicyUtility.getPolicies();

        when(policyService.getAllPolicies()).thenReturn(mockPolicies);

        mockMvc.perform(builder).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));

    }
}
