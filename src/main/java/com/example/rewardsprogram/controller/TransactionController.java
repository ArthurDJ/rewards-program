package com.example.rewardsprogram.controller;

import com.example.rewardsprogram.model.ResponseMessage;
import com.example.rewardsprogram.model.Transaction;
import com.example.rewardsprogram.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        return transaction != null ?
                ResponseEntity.ok(transaction) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Transaction> transactions = transactionService.findTransactionsByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Transaction>> getTransactionsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Transaction> transactions = transactionService.findTransactionsByDate(date);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<Transaction>> getTransactionsBetweenDates(
            @RequestParam(required = false, defaultValue = "0001-01-01")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false, defaultValue = "9999-12-31")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<Transaction> transactions = transactionService.findTransactionsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(transactions);

    }
    @GetMapping("/search/total_greater")
    public ResponseEntity<List<Transaction>> findTransactionsWithTotalGreaterThanEqual(
            @RequestParam BigDecimal total) {
        List<Transaction> transactions = transactionService.findTransactionsWithTotalGreaterThanEqual(total);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/search/total_less")
    public ResponseEntity<List<Transaction>> findTransactionsWithTotalLessThanEqual(
            @RequestParam BigDecimal total) {
        List<Transaction> transactions = transactionService.findTransactionsWithTotalLessThanEqual(total);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createTransaction(@Validated @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveOrUpdateTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Transaction has been created.", savedTransaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateTransaction(@PathVariable Long id, @Validated @RequestBody Transaction transaction) {
        Transaction currentTransaction = transactionService.findTransactionById(id);
        if (currentTransaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Transaction doesn't exist."));
        }
        transaction.setTransactionId(id);
        Transaction updatedTransaction = transactionService.saveOrUpdateTransaction(transaction);
        return ResponseEntity.ok(new ResponseMessage("Transaction updated successfully.", updatedTransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteTransaction(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Transaction not found."));
        }
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok(new ResponseMessage("Transaction has been deleted."));
    }

}
