package com.example.rewardsprogram.controller;

import com.example.rewardsprogram.exception.NotFoundException;
import com.example.rewardsprogram.exception.NumberCantNegativeException;
import com.example.rewardsprogram.model.ErrorResponse;
import com.example.rewardsprogram.model.ResponseMessage;
import com.example.rewardsprogram.model.Transaction;
import com.example.rewardsprogram.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
        if (id.compareTo(0L) <= 0) {
            throw new NumberCantNegativeException("Transaction ID should be at least One.");
        }
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            throw new NotFoundException("Transaction not found for ID: " + id);
        }
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable("customerId") Long customerId) {
        if (customerId.compareTo(0L) <= 0) {
            throw new NumberCantNegativeException("Customer ID should be at least One.");
        }
        List<Transaction> transactions = transactionService.findTransactionsByCustomerId(customerId);
        if (transactions == null || transactions.isEmpty()) {
            throw new NotFoundException("No transactions found for Customer ID: " + customerId);
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Transaction>> getTransactionsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date parameter is required.");
        }
        List<Transaction> transactions = transactionService.findTransactionsByDate(date);
        if (transactions == null || transactions.isEmpty()) {
            throw new NotFoundException("No transactions found on Date: " + date);
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/dateBetween")
    public ResponseEntity<List<Transaction>> getTransactionsBetweenDates(
            @RequestParam(required = false, defaultValue = "0001-01-01")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false, defaultValue = "9999-12-31")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<Transaction> transactions = transactionService.findTransactionsBetweenDates(startDate, endDate);
        if (transactions == null || transactions.isEmpty()) {
            throw new NotFoundException("No transactions found between dates.");
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/total_greater")
    public ResponseEntity<List<Transaction>> findTransactionsWithTotalGreaterThanEqual(
            @RequestParam BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new NumberCantNegativeException("Total should be at least Zero.");
        }
        List<Transaction> transactions = transactionService.findTransactionsWithTotalGreaterThanEqual(total);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/total_less")
    public ResponseEntity<List<Transaction>> findTransactionsWithTotalLessThanEqual(
            @RequestParam BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new NumberCantNegativeException("Total should be at least Zero.");
        }
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

    @ExceptionHandler(NumberCantNegativeException.class)
    public ResponseEntity<ErrorResponse> handleException(NumberCantNegativeException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
