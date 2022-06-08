package com.rnd.transactiondiffchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnMatchedRecord {

    private String transactionId;
    private String walletReference;
    private String transactionDate;
    private String transactionAmount;
    private String probableMatchTrxId;
}
