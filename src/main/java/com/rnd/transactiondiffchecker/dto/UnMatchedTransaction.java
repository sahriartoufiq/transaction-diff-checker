package com.rnd.transactiondiffchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnMatchedTransaction {

    private TransactionDetailDTO originalTransaction;
    private TransactionDetailDTO probableMatchTransaction;
}
