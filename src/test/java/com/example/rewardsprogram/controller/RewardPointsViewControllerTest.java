package com.example.rewardsprogram.controller;

import com.example.rewardsprogram.model.CustomerMonthlyRewards;
import com.example.rewardsprogram.model.RewardPointsView;
import com.example.rewardsprogram.service.RewardPointsViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RewardPointsViewController.class)
public class RewardPointsViewControllerTest {


    //    MockMvc provides support for Spring MVC testing.
    //    It allows you to send HTTP requests into the DispatcherServlet and make assertions about the result.
    @Autowired
    private MockMvc mockMvc;


    //    Mock the RewardPointsViewService since you want to isolate your tests to only the controller layer.
    @MockBean
    private RewardPointsViewService rewardPointsViewService;

    @Test
    public void getRewardPointsTest() throws Exception {
        List<RewardPointsView> mockResponse = new ArrayList<>();
        given(rewardPointsViewService.findALLRewardPoints()).willReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewardPoints").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(mockResponse.size())));
    }

    @Test
    public void getRewardPointsByCustomerIdTest() throws Exception {
        Long customerId = 1L;
        List<RewardPointsView> mockResponse = new ArrayList<>();
        given(rewardPointsViewService.findRewardPointsByCustomerId(customerId)).willReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewardPoints/customer/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockResponse.size())));
    }

    @Test
    public void getMonthlyRewardsTest() throws Exception {
        int months = 3;
        List<CustomerMonthlyRewards> mockResponse = new ArrayList<>();
        given(rewardPointsViewService.calculateMonthlyRewardsForAllCustomers(months)).willReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewardPoints/monthlyRewards")
                        .param("months", String.valueOf(months))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockResponse.size())));
    }

}
