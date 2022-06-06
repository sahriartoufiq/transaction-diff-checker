package com.rnd.transactiondiffchecker.service;

import com.rnd.transactiondiffchecker.dto.TransactionDetailDTO;
import org.springframework.stereotype.Service;

@Service
public class HelperService {

    public String getTrxKeyWithTrxId(TransactionDetailDTO transaction) {
        return String.format("%s-%s",
                transaction.getTransactionID(),
                transaction.getTransactionType());
    }

    public String getTrxKeyWithoutTrxId(TransactionDetailDTO transaction) {
        return String.format("%s-%s-%s-%s",
                transaction.getWalletReference(),
                transaction.getTransactionType(),
                transaction.getTransactionAmount(),
                transaction.getTransactionDate());
    }
}
