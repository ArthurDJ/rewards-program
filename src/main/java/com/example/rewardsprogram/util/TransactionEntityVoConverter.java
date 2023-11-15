package com.example.rewardsprogram.util;

import com.example.rewardsprogram.entity.TransactionEntity;
import com.example.rewardsprogram.model.Transaction;

public class TransactionEntityVoConverter {

    public static Transaction convertEntityToVo(TransactionEntity transactionEntity) {
        if (transactionEntity == null) {
            return null;
        }
        Transaction transactionVo = new Transaction();
        transactionVo.setTransactionId(transactionEntity.getTransactionId());
        transactionVo.setCustomerId(transactionEntity.getCustomerEntity().getCustomerId()); // Assuming CustomerEntity has a getCustomerId method
        transactionVo.setTransactionDate(transactionEntity.getTransactionDate());
        transactionVo.setTotal(transactionEntity.getTotal());
        return transactionVo;
    }


    public static TransactionEntity convertVoToEntity(Transaction transactionVo) {
        if (transactionVo == null) {
            return null;
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionVo.getTransactionId());
        transactionEntity.setTransactionDate(transactionVo.getTransactionDate());
        transactionEntity.setTotal(transactionVo.getTotal());
        return transactionEntity;
    }

}
