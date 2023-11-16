package com.example.rewardsprogram.controller;


import com.example.rewardsprogram.exception.NumberCantNegativeException;
import com.example.rewardsprogram.model.Transaction;
import com.example.rewardsprogram.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private TransactionService transactionService;

    @Test
    public void findTransactionsWithTotalGreaterThanEqual_Success() throws Exception {
        BigDecimal total = new BigDecimal("10");
        List<Transaction> mockTransactions = new ArrayList<>();
        // Populate mockTransactions with test data

        given(transactionService.findTransactionsWithTotalGreaterThanEqual(total)).willReturn(mockTransactions);

        mockMvc.perform(get("/api/transactions/total_greater")
                        .param("total", total.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockTransactions.size())));
    }

    @Test
    public void findTransactionsWithTotalGreaterThanEqual_NegativeTotal() throws Exception {
        BigDecimal negativeTotal = new BigDecimal("-100.00");

        mockMvc.perform(get("/api/transactions/total_greater")
                        .param("total", negativeTotal.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Total should be at least Zero."));
    }

    @Test
    public void getTransactionById_NotFound_ThrowsNotFoundException() throws Exception {
        Long nonExistentId = 999L;

        mockMvc.perform(get("/api/transactions/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Transaction not found for ID: " + nonExistentId));
    }

    @Test
    public void getTransactionById_WhenValid_ReturnsTransaction() throws Exception {
        Long transactionId = 10L;
        Transaction mockTransaction = new Transaction(); // Create and configure a mock Transaction object

        given(transactionService.findTransactionById(transactionId)).willReturn(mockTransaction);

        mockMvc.perform(get("/api/transactions/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(mockTransaction.getTransactionId()));
    }

    @Test
    public void getTransactionById_WhenInvalidId_ThrowsException() throws Exception {
        Long invalidId = -1L;

        mockMvc.perform(get("/api/transactions/{id}", invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Transaction ID should be at least One."));
    }

    @Test
    public void getTransactionsByCustomerId_WhenValid_ReturnsTransactions() throws Exception {
        Long validCustomerId = 2L;
        List<Transaction> mockTransactions = new ArrayList<>();

        given(transactionService.findTransactionsByCustomerId(validCustomerId)).willReturn(mockTransactions);

        mockMvc.perform(get("/api/transactions/customers/{customerId}", validCustomerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockTransactions.size())));
    }

    @Test
    public void getTransactionsByCustomerId_WhenInvalidId_ThrowsException() throws Exception {
        Long invalidCustomerId = -1L;

        mockMvc.perform(get("/api/transactions/customers/{customerId}", invalidCustomerId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Customer ID should be at least One."));
    }

    @Test
    public void getTransactionsByDate_WhenValidDate_ReturnsTransactions() throws Exception {
        String dateString = "2023-10-30";
        Date validDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        List<Transaction> mockTransactions = new ArrayList<>();

        given(transactionService.findTransactionsByDate(validDate)).willReturn(mockTransactions);

        mockMvc.perform(get("/api/transactions/date")
                        .param("date", "2023-10-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockTransactions.size())));
    }

    @Test
    public void getTransactionsByDate_WhenNoTransactions_ThrowsNotFoundException() throws Exception {
        String dateString = "1000-10-30";
        Date validDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        given(transactionService.findTransactionsByDate(validDate)).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transactions/date")
                        .param("date", dateString))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No transactions found on Date: " + validDate));
    }

    @Test
    public void getTransactionsBetweenDates_WhenValidDates_ReturnsTransactions() throws Exception {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-31");
        List<Transaction> mockTransactions = new ArrayList<>();

        given(transactionService.findTransactionsBetweenDates(startDate, endDate)).willReturn(mockTransactions);

        mockMvc.perform(get("/api/transactions/dateBetween")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockTransactions.size())));
    }


}
