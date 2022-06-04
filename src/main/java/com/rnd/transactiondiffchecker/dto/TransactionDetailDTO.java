package com.rnd.transactiondiffchecker.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.rnd.transactiondiffchecker.constants.ReconciliationFileHeaderKey.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailDTO {

    //@CsvBindByName(column = PROFILE_NAME)
    @CsvBindByPosition(position = 0)
    private String profileName;
    // @CsvBindByName(column = TRANSACTION_DATE)
    @CsvBindByPosition(position = 1)
    private String transactionDate;
    //@CsvBindByName(column = TRANSACTION_AMOUNT)
    @CsvBindByPosition(position = 2)
    private String transactionAmount;
    //@CsvBindByName(column = TRANSACTION_NARRATIVE)
    @CsvBindByPosition(position = 3)
    private String transactionNarrative;
    //@CsvBindByName(column = TRANSACTION_DESCRIPTION)
    @CsvBindByPosition(position = 4)
    private String transactionDescription;
    //@CsvBindByName(column = TRANSACTION_ID)
    @CsvBindByPosition(position = 5)
    private String transactionID;
    //@CsvBindByName(column = TRANSACTION_TYPE)
    @CsvBindByPosition(position = 6)
    private String transactionType;
    //@CsvBindByName(column = WALLET_REFERENCE)
    @CsvBindByPosition(position = 7)
    private String walletReference;
}
